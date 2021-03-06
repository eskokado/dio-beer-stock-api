package org.esk.diobeerstockapi.mappers;

import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.dtos.OrderWithoutItemsDTO;
import org.esk.diobeerstockapi.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderWhitoutItemsMapper {
    OrderWhitoutItemsMapper INSTANCE = Mappers.getMapper(OrderWhitoutItemsMapper.class);

    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    Order toModel(OrderWithoutItemsDTO orderWithoutItemsDTO);

    OrderWithoutItemsDTO toDTO(Order order);
}
