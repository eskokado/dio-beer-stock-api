package org.esk.diobeerstockapi.mappers;

import org.esk.diobeerstockapi.dtos.OrderItemDTO;
import org.esk.diobeerstockapi.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItem toModel(OrderItemDTO orderItemDTO);

    OrderItemDTO toDTO(OrderItem orderItem);
}
