package org.example.online_products_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableProductDto {
    private Long shopId;
    private Long productId;
    private Integer count;
    private Double price;
}
