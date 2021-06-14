package org.esk.diobeerstockapi.services;

import org.esk.diobeerstockapi.builders.OrderDTOBuilder;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.dtos.OrderItemDTO;
import org.esk.diobeerstockapi.entities.Order;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.exceptions.OrderNotFoundException;
import org.esk.diobeerstockapi.mappers.OrderMapper;
import org.esk.diobeerstockapi.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private static final long INVALID_ORDER_ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    private OrderMapper orderMapper = OrderMapper.INSTANCE;

    @InjectMocks
    private OrderService orderService;

    @Test
    void whenOrderInformedThenItShouldBeCreated() throws ClientNotFoundException {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedSavedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.save(Mockito.any())).thenReturn(expectedSavedOrder);

        // then
        OrderDTO createdOrderDTO = orderService.createOrder(expectedOrderDTO);

        assertThat(createdOrderDTO.getId(), is(equalTo(expectedOrderDTO.getId())));
        assertThat(createdOrderDTO.getDate(), is(equalTo(expectedOrderDTO.getDate())));
        assertThat(createdOrderDTO.getClient(), is(equalTo(expectedOrderDTO.getClient())));
        assertThat(createdOrderDTO.getItems().size(), is(equalTo(expectedOrderDTO.getItems().size())));
        List<OrderItemDTO> createdOrderItemsDTO = new ArrayList<>(createdOrderDTO.getItems());
        List<OrderItemDTO> expectedOrderItemsDTO = new ArrayList<>(expectedOrderDTO.getItems());
        assertThat(createdOrderItemsDTO.get(0), is(equalTo(expectedOrderItemsDTO.get(0))));
    }

    @Test
    void whenUpdateIsCalledWithValidOrderUpdateGivenThenReturnAOrderUpdated() throws OrderNotFoundException {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedUpdatedOrder = orderMapper.toModel(expectedOrderDTO);

        // when
        when(orderRepository.findById(expectedOrderDTO.getId())).thenReturn(Optional.of(expectedUpdatedOrder));
        when(orderRepository.save(Mockito.any())).thenReturn(expectedUpdatedOrder);

        // then
        OrderDTO updatedOrderDTO = orderService.updateOrder(expectedOrderDTO);
        assertThat(updatedOrderDTO.getId(), is(equalTo(expectedOrderDTO.getId())));
        assertThat(updatedOrderDTO.getDate(), is(equalTo(expectedOrderDTO.getDate())));
        assertThat(updatedOrderDTO.getClient(), is(equalTo(expectedOrderDTO.getClient())));
        assertThat(updatedOrderDTO.getItems().size(), is(equalTo(expectedOrderDTO.getItems().size())));
        List<OrderItemDTO> createdOrderItemsDTO = new ArrayList<>(updatedOrderDTO.getItems());
        List<OrderItemDTO> expectedOrderItemsDTO = new ArrayList<>(expectedOrderDTO.getItems());
        assertThat(createdOrderItemsDTO.get(0), is(equalTo(expectedOrderItemsDTO.get(0))));
    }

    @Test
    void whenListOrderIsCalledThenReturnAListOfOrder() {
        // given
        OrderDTO expectedFoundOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedFoundOrder = orderMapper.toModel(expectedFoundOrderDTO);

        // when
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundOrder));

        // then
        List<OrderDTO> foundListOrdersDTO = orderService.listAll();

        assertThat(foundListOrdersDTO, is(not(empty())));
        assertThat(foundListOrdersDTO.get(0), is(equalTo(expectedFoundOrderDTO)));
    }

    @Test
    void whenListOrderIsCalledThenReturnAnEmptyListOfOrders() {
        // when
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<OrderDTO> foundListOrdersDTO = orderService.listAll();

        assertThat(foundListOrdersDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenAOrderShouldBeDeleted() throws OrderNotFoundException {
        // given
        OrderDTO expectedDeletedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        Order expectedDeletedOrder = orderMapper.toModel(expectedDeletedOrderDTO);

        // when
        when(orderRepository.findById(expectedDeletedOrderDTO.getId())).thenReturn(Optional.of(expectedDeletedOrder));
        doNothing().when(orderRepository).deleteById(expectedDeletedOrderDTO.getId());

        // then
        orderService.deleteById(expectedDeletedOrderDTO.getId());

        verify(orderRepository, times(1)).findById(expectedDeletedOrderDTO.getId());
        verify(orderRepository, times(1)).deleteById(expectedDeletedOrderDTO.getId());
    }
}
