package org.esk.diobeerstockapi.controllers;

import org.esk.diobeerstockapi.builders.ClientDTOBuilder;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.exceptions.ClientAlreadyRegisteredException;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.services.ClientService;
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

import java.util.Collections;

import static org.esk.diobeerstockapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    private static final String CLIENT_API_URL_PATH = "/api/v1/clients";
    private static final long VALID_CLIENT_ID = 1L;
    private static final long INVALID_CLIENT_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.createClient(clientDTO)).thenReturn(clientDTO);

        // then
        mockMvc.perform(post(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(clientDTO.getName())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        clientDTO.setName(null);

        // then
        mockMvc.perform(post(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.findById(clientDTO.getId())).thenReturn(clientDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/id/" + clientDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(clientDTO.getName())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.findById(clientDTO.getId())).thenThrow(ClientNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/id/" + clientDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.findByName(clientDTO.getName())).thenReturn(clientDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/name/" + clientDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(clientDTO.getName())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.findByName(clientDTO.getName())).thenThrow(ClientNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/name/" + clientDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithClientsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientService.listAll()).thenReturn(Collections.singletonList(clientDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(clientDTO.getName())));
    }

    @Test
    void thenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        doNothing().when(clientService).deleteById(clientDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CLIENT_API_URL_PATH + "/" + clientDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void thenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
        doThrow(ClientNotFoundException.class).when(clientService).deleteById(INVALID_CLIENT_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CLIENT_API_URL_PATH + "/" + INVALID_CLIENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTIsCalledThenAClientIsOkStatusReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        clientDTO.setName("Modified Name");

        // when
        when(clientService.updateClient(clientDTO)).thenReturn(clientDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CLIENT_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Modified Name")));
    }

    @Test
    void thenPUTIsCalledWithInvalidIdIsNotFoundStatusReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        clientDTO.setId(INVALID_CLIENT_ID);

        // when
        when(clientService.updateClient(clientDTO)).thenThrow(ClientNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void thenPUTisCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        ClientDTO clientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        clientDTO.setName(null);

        // when
        when(clientService.updateClient(clientDTO)).thenThrow(ClientAlreadyRegisteredException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isBadRequest());
    }
}
