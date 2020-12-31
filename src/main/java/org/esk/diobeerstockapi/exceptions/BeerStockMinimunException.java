package org.esk.diobeerstockapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockMinimunException extends Exception {

    public BeerStockMinimunException(Long id, int quantityToDecrement) {
        super(String.format("Beers with %s ID to decrement informed exceeds the min stock capacity: %s", id, quantityToDecrement));
    }
}