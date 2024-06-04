package org.example.online_products_shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShopDto {
    private Long districtId;
    private Long adminId;
}
