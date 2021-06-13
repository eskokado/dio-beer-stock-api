package org.esk.diobeerstockapi.services;

import org.esk.diobeerstockapi.builders.OrderDTOBuilder;
import org.esk.diobeerstockapi.builders.OrderItemDTOBuilder;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.dtos.OrderItemDTO;
import org.esk.diobeerstockapi.entities.Order;
import org.esk.diobeerstockapi.mappers.OrderMapper;
import org.esk.diobeerstockapi.repositories.OrderItemRepository;
import org.esk.diobeerstockapi.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private static final long INVALID_ORDER_ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    private OrderMapper orderMapper = OrderMapper.INSTANCE;

    @InjectMocks
    private OrderService orderService;

    @Test
    void whenOrderInformedThenItShouldBeCreated() {
        // given
        OrderDTO expectedOrderDTO = OrderDTOBuilder.builder().build().toOrderDTO();
        OrderItemDTO expectedOrderItemDTO = OrderItemDTOBuilder.builder().build().toOrderItemDTO();
        expectedOrderDTO.setItems(Set.of(expectedOrderItemDTO));
        Order expectedOrder = orderMapper.toModel(expectedOrderDTO);
    }
}
