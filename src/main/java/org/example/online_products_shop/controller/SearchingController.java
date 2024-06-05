package org.example.online_products_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Searching Products API",
        description = "Designed to searching products"
)
public class SearchingController {
    private final AvailableProductService availableProductService;

    @PostMapping
    @Operation(summary = "Search Product", description = "First, the product is searched in the district " +
            "where the user is currently located, if it is not found there, it is searched in the whole city."
    )
    public ResponseEntity<AvailableProduct> searching(@RequestBody SearchingDto searchingDto) {
        return ResponseEntity.ok(availableProductService.getAvailableProductForSearching(searchingDto));
    }
}
