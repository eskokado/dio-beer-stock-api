package org.esk.diobeerstockapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(Long id) {
        super(String.format("Order with id %s not found in the system.", id));
    }
}