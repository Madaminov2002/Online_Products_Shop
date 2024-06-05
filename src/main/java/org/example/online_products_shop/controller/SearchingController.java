package org.example.online_products_shop.controller;

import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.AvailableProduct;
import org.example.online_products_shop.dto.SearchingDto;
import org.example.online_products_shop.service.AvailableProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searching")
@RequiredArgsConstructor
public class SearchingController {
    private final AvailableProductService availableProductService;

    @PostMapping
    public ResponseEntity<AvailableProduct> searching(@RequestBody SearchingDto searchingDto) {
        return ResponseEntity.ok(availableProductService.getAvailableProductForSearching(searchingDto));
    }
}
