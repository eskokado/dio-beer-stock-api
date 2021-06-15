package org.esk.diobeerstockapi.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.entities.Client;
import org.esk.diobeerstockapi.exceptions.ClientAlreadyRegisteredException;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.mappers.ClientMapper;
import org.esk.diobeerstockapi.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    public ClientDTO createClient(ClientDTO clientDTO) throws ClientAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(clientDTO.getName());
        Client client = clientMapper.toModel(clientDTO);
        client.setId(null);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    public ClientDTO updateClient(ClientDTO clientDTO) throws ClientNotFoundException, ClientAlreadyRegisteredException {
        verifyIfExists(clientDTO.getId());
        Client clientToUpdate = clientMapper.toModel(clientDTO);
        Client updatedClient = clientRepository.save(clientToUpdate);
        return clientMapper.toDTO(updatedClient);
    }

    public ClientDTO findByName(String name) throws ClientNotFoundException {
        Client foundClient = clientRepository.findByName(name).orElseThrow(() -> new ClientNotFoundException(name));
        return clientMapper.toDTO(foundClient);
    }

    public ClientDTO findById(Long id) throws ClientNotFoundException {
        Client foundClient = verifyIfExists(id);
        return clientMapper.toDTO(foundClient);
    }

    public List<ClientDTO> listAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws ClientNotFoundException {
        verifyIfExists(id);
        clientRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws ClientAlreadyRegisteredException {
        Optional<Client> optSavedClient = clientRepository.findByName(name);
        if (optSavedClient.isPresent()) {
            throw new ClientAlreadyRegisteredException(name);
        }
    }

    private Client verifyIfExists(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

}
