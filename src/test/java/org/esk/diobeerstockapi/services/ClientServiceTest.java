package org.esk.diobeerstockapi.services;

import lombok.With;
import org.esk.diobeerstockapi.builders.BeerDTOBuilder;
import org.esk.diobeerstockapi.builders.ClientDTOBuilder;
import org.esk.diobeerstockapi.dtos.BeerDTO;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.entities.Beer;
import org.esk.diobeerstockapi.entities.Client;
import org.esk.diobeerstockapi.exceptions.BeerNotFoundException;
import org.esk.diobeerstockapi.exceptions.ClientAlreadyRegisteredException;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.mappers.ClientMapper;
import org.esk.diobeerstockapi.repositories.ClientRepository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private static final long INVALID_CLIENT_ID = 1L;

    @Mock
    private ClientRepository clientRepository;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @InjectMocks
    private ClientService clientService;

    @Test
    void whenClientInformedThenItShouldBeCreated() throws ClientAlreadyRegisteredException {
        // given
        ClientDTO expectedClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client expectedSavedClient = clientMapper.toModel(expectedClientDTO);

        // when
        when(clientRepository.findByName(expectedClientDTO.getName())).thenReturn(Optional.empty());
        when(clientRepository.save(expectedSavedClient)).thenReturn(expectedSavedClient);

        //then
        ClientDTO createdClientDTO = clientService.createClient(expectedClientDTO);

        assertThat(createdClientDTO.getId(), is(equalTo(expectedClientDTO.getId())));
        assertThat(createdClientDTO.getName(), is(equalTo(expectedClientDTO.getName())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        // given
        ClientDTO expectedClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client duplicatedClient = clientMapper.toModel(expectedClientDTO);

        // when
        when(clientRepository.findByName(expectedClientDTO.getName())).thenReturn(Optional.of(duplicatedClient));

        // then
        assertThrows(ClientAlreadyRegisteredException.class, () -> clientService.createClient(expectedClientDTO));
    }


    @Test
    void whenValidClientNameIsGivenThenReturnAClient() throws ClientNotFoundException {
        // given
        ClientDTO expectedFoundClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client expectedFoundClient = clientMapper.toModel(expectedFoundClientDTO);

        // when
        when(clientRepository.findByName(expectedFoundClient.getName())).thenReturn(Optional.of(expectedFoundClient));

        // then
        ClientDTO foundClientDTO = clientService.findByName(expectedFoundClientDTO.getName());

        assertThat(foundClientDTO, is(equalTo(expectedFoundClientDTO)));
    }

    @Test
    void whenNotRegisteredClientNameIsGivenThenThrowAnException() {
        // given
        ClientDTO expectedFoundClientDTO = ClientDTOBuilder.builder().build().toClientDTO();

        // when
        when(clientRepository.findByName(expectedFoundClientDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(ClientNotFoundException.class, () -> clientService.findByName(expectedFoundClientDTO.getName()));
    }

    @Test
    void whenUpdateIsCalledWithValidIdAndClientUpdateGivenThenReturnAClienteUpdated() throws ClientNotFoundException {
        // given
        ClientDTO expectedUpdatedClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client expectedUpdatedClient = clientMapper.toModel(expectedUpdatedClientDTO);

        // when
        when(clientRepository.findById(expectedUpdatedClientDTO.getId())).thenReturn(Optional.of(expectedUpdatedClient));
        when(clientRepository.save(expectedUpdatedClient)).thenReturn(expectedUpdatedClient);

        //then
        ClientDTO updatedClientDTO = clientService.updateById(expectedUpdatedClientDTO.getId(), expectedUpdatedClientDTO);

        assertThat(updatedClientDTO.getId(), is(equalTo(expectedUpdatedClientDTO.getId())));
        assertThat(updatedClientDTO.getName(), is(equalTo(expectedUpdatedClientDTO.getName())));
    }

    @Test
    void whenListClientIsCalledThenReturnAListOfClient() {
        // given
        ClientDTO expectedFoundClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client expectedFoundClient = clientMapper.toModel(expectedFoundClientDTO);

        // when
        when(clientRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundClient));

        // then
        List<ClientDTO> foundListClientsDTO = clientService.listAll();

        assertThat(foundListClientsDTO, is(not(empty())));
        assertThat(foundListClientsDTO.get(0), is(equalTo(expectedFoundClientDTO)));
    }

    @Test
    void whenListClientIsCalledThenReturnAnEmptyListOfClients() {
        // when
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<ClientDTO> foundListClientsDTO = clientService.listAll();

        assertThat(foundListClientsDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws ClientNotFoundException {
        // given
        ClientDTO expectedDeletedClientDTO = ClientDTOBuilder.builder().build().toClientDTO();
        Client expectedDeletedClient = clientMapper.toModel(expectedDeletedClientDTO);

        // when
        when(clientRepository.findById(expectedDeletedClientDTO.getId())).thenReturn(Optional.of(expectedDeletedClient));
        doNothing().when(clientRepository).deleteById(expectedDeletedClientDTO.getId());

        // then
        clientService.deleteById(expectedDeletedClientDTO.getId());

        verify(clientRepository, times(1)).findById(expectedDeletedClientDTO.getId());
        verify(clientRepository, times(1)).deleteById(expectedDeletedClientDTO.getId());
    }
}
