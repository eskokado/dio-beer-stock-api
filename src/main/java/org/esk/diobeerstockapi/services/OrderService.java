package org.esk.diobeerstockapi.services;

import lombok.AllArgsConstructor;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.entities.Order;
import org.esk.diobeerstockapi.entities.OrderItem;
import org.esk.diobeerstockapi.exceptions.ClientNotFoundException;
import org.esk.diobeerstockapi.exceptions.OrderNotFoundException;
import org.esk.diobeerstockapi.mappers.OrderMapper;
import org.esk.diobeerstockapi.repositories.OrderItemRepository;
import org.esk.diobeerstockapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientService clientService;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    public OrderDTO createOrder(OrderDTO orderDTO) throws ClientNotFoundException {
        Order order = orderMapper.toModel(orderDTO);
        order.setId(null);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) throws OrderNotFoundException {
        verifyIfExists(orderDTO.getId());
        Order orderToUpdate = orderMapper.toModel(orderDTO);
        Order orderUpdated = orderRepository.save(orderToUpdate);
        return orderMapper.toDTO(orderUpdated);
    }

    public OrderDTO findById(Long id) throws OrderNotFoundException {
        return orderMapper.toDTO(verifyIfExists(id));
    }

    private Order verifyIfExists(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<OrderDTO> listAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws OrderNotFoundException {
        verifyIfExists(id);
        orderRepository.deleteById(id);
    }
}
