package org.esk.diobeerstockapi.dtos;

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
@AllArgsConstructor
public class OrderWithoutItemsDTO {
    private Long id;

    @NotEmpty
    private LocalDate date;

    @NotEmpty
    private ClientDTO client;
}
