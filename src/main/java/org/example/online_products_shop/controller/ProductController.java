package org.example.online_products_shop.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.Product;
import org.example.online_products_shop.dto.ProductDto;
import org.example.online_products_shop.service.ProductService;
import org.example.online_products_shop.updatedto.ProductUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Product> save(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @GetMapping("/show_all/products")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> showAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Product> update(@RequestBody ProductUpdateDto updateDto) {
        return ResponseEntity.ok(productService.productUpdate(updateDto));
    }
}
