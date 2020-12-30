package org.esk.diobeerstockapi.mappers;

import org.esk.diobeerstockapi.dtos.BeerDTO;
import org.esk.diobeerstockapi.entities.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {
    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);
}
