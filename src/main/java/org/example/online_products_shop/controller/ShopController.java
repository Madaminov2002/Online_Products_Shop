package org.example.online_products_shop.controller;

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
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Shop> save(@RequestBody ShopDto shopDto) {
        return ResponseEntity.ok(shopService.save(shopDto));
    }

}
