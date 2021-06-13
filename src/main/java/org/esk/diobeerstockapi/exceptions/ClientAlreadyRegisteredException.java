package org.esk.diobeerstockapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientAlreadyRegisteredException extends Exception{

    public ClientAlreadyRegisteredException(String clientName) {
        super(String.format("Client with name %s already registered in the system.", clientName));
    }
}