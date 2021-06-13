package org.esk.diobeerstockapi.services;

import lombok.AllArgsConstructor;
import org.esk.diobeerstockapi.mappers.OrderItemMapper;
import org.esk.diobeerstockapi.mappers.OrderMapper;
import org.esk.diobeerstockapi.repositories.OrderItemRepository;
import org.esk.diobeerstockapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
}
