package org.esk.diobeerstockapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    @NotEmpty
    private String date;

    @NotEmpty
    private ClientDTO client;

    @NotEmpty
    private Set<OrderItemDTO> items = new HashSet<>();

    public OrderDTO(Long id, String date, ClientDTO client, Set<OrderItemDTO> items) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.items = items;
    }
}
