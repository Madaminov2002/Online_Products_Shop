package org.example.online_products_shop.updatedto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateDto {
    private Long id;
    private String name;
    private String farm;
    private LocalDateTime dateOfManufacture;
    private LocalDateTime dateOfExpiry;
}