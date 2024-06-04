package org.example.online_products_shop.updatedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableProductUpdateDto {
    private Long id;
    private Integer count;
    private Double price;
}
