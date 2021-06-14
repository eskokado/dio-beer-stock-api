package org.esk.diobeerstockapi.builders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.dtos.OrderItemDTO;
import org.esk.diobeerstockapi.dtos.OrderDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Builder
public class OrderDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Builder.Default
    private ClientDTO client = ClientDTOBuilder.builder().build().toClientDTO();

    @Builder.Default
    private Set<OrderItemDTO> items = Collections.singleton(OrderItemDTOBuilder.builder().build().toOrderItemDTO());

    public OrderDTO toOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setDate(date);
        orderDTO.setClient(client);
        orderDTO.setItems(items);
        return orderDTO;
    }
}
