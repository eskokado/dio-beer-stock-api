package org.esk.diobeerstockapi.repositories;

import org.esk.diobeerstockapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Order, Long> {
}