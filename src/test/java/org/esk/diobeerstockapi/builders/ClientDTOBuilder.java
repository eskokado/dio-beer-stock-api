package org.esk.diobeerstockapi.builders;

import lombok.Builder;
import org.esk.diobeerstockapi.dtos.ClientDTO;

@Builder
public class ClientDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Restaurant Rod Raposo Tavares";

    public ClientDTO toClientDTO() {
        return new ClientDTO(id,
                name);
    }
}
