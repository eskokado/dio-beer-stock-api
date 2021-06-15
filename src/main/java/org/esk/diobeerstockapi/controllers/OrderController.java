package org.esk.diobeerstockapi.controllers;

import lombok.AllArgsConstructor;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.exceptions.OrderNotFoundException;
import org.esk.diobeerstockapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) throws ClientNotFoundException {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable Long id) throws OrderNotFoundException {
        return orderService.findById(id);
    }

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws OrderNotFoundException {
        orderService.deleteById(id);
    }

    @PutMapping
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO) throws OrderNotFoundException {
        return orderService.updateOrder(orderDTO);
    }
}
