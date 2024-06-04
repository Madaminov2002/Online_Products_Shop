package org.example.online_products_shop.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDto {
    private String productName;
    private LocalDateTime dateOfManufacture;
    private LocalDateTime dateOfExpiry;
    private String farm;

}
