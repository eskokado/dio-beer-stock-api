package org.esk.diobeerstockapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esk.diobeerstockapi.entities.OrderItemPK;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderItemPK.class)
public class OrderItemDTO {
    @NotEmpty
    @Id
    private OrderDTO order;

    @NotEmpty
    @Id
    private BeerDTO beer;

    private Double discount;

    @NotEmpty
    private Integer amount;

    @NotEmpty
    private Double price;
}
