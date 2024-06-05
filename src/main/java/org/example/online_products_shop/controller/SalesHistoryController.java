package org.example.online_products_shop.controller;

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
public class SalesHistoryController {
    private final SalesHistoryService salesHistoryService;

    /**
     * @param productId
     * @param shopId
     * @param count     example:/save/productID/{pId}?count=2/shopID/{shId}
     */
    @PostMapping("/save/productID/{pId}/shopID/{shId}")
    public ResponseEntity<SalesHistory> save(
            @PathVariable("pId") Long productId, @PathVariable("shId") Long shopId,
            @RequestParam("count") Integer count
    ) {
        return ResponseEntity.ok(salesHistoryService.save(productId, shopId, count));
    }
}
