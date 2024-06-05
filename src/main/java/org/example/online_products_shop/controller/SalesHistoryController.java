package org.example.online_products_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.SalesHistory;
import org.example.online_products_shop.service.SalesHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salesHistory")
@Tag(
        name = "Sales History API",
        description = "Designed to showing information of sales history"
)
public class SalesHistoryController {
    private final SalesHistoryService salesHistoryService;

    /**
     * @param productId
     * @param shopId
     * @param count     example:/save/productID/{pId}?count=2/shopID/{shId}
     */
    @PostMapping("/save/productID/{pId}/shopID/{shId}")
    @Operation(summary = "Save History", description = "saves the sold products in the table")
    public ResponseEntity<SalesHistory> save(
            @PathVariable("pId") Long productId, @PathVariable("shId") Long shopId,
            @RequestParam("count") Integer count
    ) {
        return ResponseEntity.ok(salesHistoryService.save(productId, shopId, count));
    }
}
