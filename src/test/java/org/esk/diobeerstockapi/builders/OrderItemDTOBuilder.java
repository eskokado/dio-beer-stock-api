package org.esk.diobeerstockapi.builders;

import lombok.Builder;
import org.esk.diobeerstockapi.dtos.OrderItemDTO;

@Builder
public class OrderItemDTOBuilder {
    @Builder.Default
    private static Long id = 1L;

    @Builder.Default
    private OrderItemDTO orderItem = new OrderItemDTO();

    public OrderItemDTO toOrderItemDTO() {
        orderItem.setOrder(OrderWithoutItemsDTOBuilder.builder().build().toOrderWithoutItemsDTO());
        orderItem.setBeer(BeerDTOBuilder.builder().build().toBeerDTO());
        orderItem.setAmount(1);
        orderItem.setPrice(15.0);
        return orderItem;
    }
}
