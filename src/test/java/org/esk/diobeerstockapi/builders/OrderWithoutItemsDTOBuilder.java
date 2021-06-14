package org.esk.diobeerstockapi.builders;

import lombok.Builder;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.dtos.OrderDTO;
import org.esk.diobeerstockapi.dtos.OrderItemDTO;
import org.esk.diobeerstockapi.dtos.OrderWithoutItemsDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Builder
public class OrderWithoutItemsDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Builder.Default
    private ClientDTO client = ClientDTOBuilder.builder().build().toClientDTO();

    public OrderWithoutItemsDTO toOrderWithoutItemsDTO() {
        OrderWithoutItemsDTO orderWithoutItemsDTO = new OrderWithoutItemsDTO();
        orderWithoutItemsDTO.setId(id);
        orderWithoutItemsDTO.setDate(date);
        orderWithoutItemsDTO.setClient(client);
        return orderWithoutItemsDTO;
    }
}
