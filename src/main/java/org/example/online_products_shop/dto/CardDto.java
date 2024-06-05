package org.example.online_products_shop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardDto {
    private String cardNumber;
    private BigDecimal sum;
}
