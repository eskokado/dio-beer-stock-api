package org.esk.diobeerstockapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends Exception {

    public ClientNotFoundException(String clientName) {
        super(String.format("Client with name %s not found in the system.", clientName));
    }

    public ClientNotFoundException(Long id) {
        super(String.format("Client with id %s not found in the system.", id));
    }
}