package org.esk.diobeerstockapi.mappers;

import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toModel(OrderDTO orderDTO);

    OrderDTO toDTO(Order order);
}
