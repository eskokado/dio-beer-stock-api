package org.esk.diobeerstockapi.controllers;

import lombok.AllArgsConstructor;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.exceptions.ClientAlreadyRegisteredException;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO createClient(@RequestBody @Valid ClientDTO clientDTO) throws ClientAlreadyRegisteredException {
        return clientService.createClient(clientDTO);
    }

    @GetMapping("/id/{id}")
    public ClientDTO findById(@PathVariable Long id) throws ClientNotFoundException {
        return clientService.findById(id);
    }

    @GetMapping("/name/{name}")
    public ClientDTO findByName(@PathVariable String name) throws ClientNotFoundException {
        return clientService.findByName(name);
    }

    @GetMapping
    public List<ClientDTO> listAll() {
        return clientService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws ClientNotFoundException {
        clientService.deleteById(id);
    }

    @PutMapping
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO) throws ClientNotFoundException, ClientAlreadyRegisteredException {
        return clientService.updateClient(clientDTO);
    }
}
