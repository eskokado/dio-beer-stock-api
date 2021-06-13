package org.esk.diobeerstockapi.mappers;

import org.esk.diobeerstockapi.dtos.ClientDTO;
import org.esk.diobeerstockapi.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toModel(ClientDTO clientDTO);

    ClientDTO toDTO(Client client);
}
