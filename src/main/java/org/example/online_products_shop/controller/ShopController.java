package org.example.online_products_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.Shop;
import org.example.online_products_shop.dto.ShopDto;
import org.example.online_products_shop.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Tag(
        name = "Shop API",
        description = "Designed to manage of shop"
)
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Save Shop", description = "Super admin adds all shops in the city along with shop admins")
    public ResponseEntity<Shop> save(@RequestBody ShopDto shopDto) {
        return ResponseEntity.ok(shopService.save(shopDto));
    }

}
