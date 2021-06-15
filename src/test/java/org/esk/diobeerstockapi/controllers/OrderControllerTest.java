package org.esk.diobeerstockapi.controllers;

import org.esk.diobeerstockapi.builders.OrderDTOBuilder;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.exceptions.OrderNotFoundException;
import org.esk.diobeerstockapi.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.Collections;

import static org.esk.diobeerstockapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private static final String ORDER_API_URL_PATH = "/api/v1/orders";
    private static final long VALID_ORDER_ID = 1L;
    private static final long INVALID_ORDER_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private OrderDTO orderDTOMock;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void thenPOSTIsCalledThenAOrderIsCreated() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();

        // when
        when(orderService.createOrder(orderDTO)).thenReturn(orderDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(ORDER_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.date", is(orderDTO.getDate())))
                .andExpect(jsonPath("$.client.name", is(orderDTO.getClient().getName())))
                .andExpect(jsonPath("$.items.*", hasSize(1)));
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();

        // when
        when(orderService.findById(orderDTO.getId())).thenReturn(orderDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_API_URL_PATH + "/" + orderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(orderDTO.getDate())))
                .andExpect(jsonPath("$.client.name", is(orderDTO.getClient().getName())))
                .andExpect(jsonPath("$.items.*", hasSize(1)));
    }

    @Test
    void whenGETIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        when(orderService.findById(INVALID_ORDER_ID)).thenThrow(OrderNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_API_URL_PATH + "/" + INVALID_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithOrdersIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();

        // when
        when(orderService.listAll()).thenReturn(Collections.singletonList(orderDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(ORDER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is(orderDTO.getDate())))
                .andExpect(jsonPath("$[0].items.*", hasSize(1)));
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();

        // when
        doNothing().when(orderService).deleteById(orderDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ORDER_API_URL_PATH + "/" + orderDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(OrderNotFoundException.class).when(orderService).deleteById(INVALID_ORDER_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(ORDER_API_URL_PATH + "/" + INVALID_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTIsCalledThenAOrderIsOkStatusReturned() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        orderDTO.setDate("2021-06-16");

        // when
        when(orderService.updateOrder(orderDTO)).thenReturn(orderDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(ORDER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(orderDTO.getDate())))
                .andExpect(jsonPath("$.client.name", is(orderDTO.getClient().getName())))
                .andExpect(jsonPath("$.date", is(orderDTO.getDate())))
                .andExpect(jsonPath("$.items.*", hasSize(1)));
    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenNotFoundStatusReturned() throws Exception {
        // given
        OrderDTO orderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        orderDTO.setId(INVALID_ORDER_ID);

        // when
        when(orderService.updateOrder(orderDTO)).thenThrow(OrderNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(ORDER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(orderDTO)))
                .andExpect(status().isNotFound());
    }
}
