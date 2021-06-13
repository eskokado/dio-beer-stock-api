package org.esk.diobeerstockapi.builders;

import lombok.Builder;
import org.esk.diobeerstockapi.dtos.BeerDTO;
import org.esk.diobeerstockapi.enums.BeerType;

@Builder
public class BeerDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Brahma";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private Double price = 15.0;

    @Builder.Default
    private BeerType type = BeerType.LAGER;

    public BeerDTO toBeerDTO() {
        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setId(id);
        beerDTO.setName(name);
        beerDTO.setBrand(brand);
        beerDTO.setMax(max);
        beerDTO.setQuantity(quantity);
        beerDTO.setType(type);
        beerDTO.setPrice(price);
        return beerDTO;
    }
}
