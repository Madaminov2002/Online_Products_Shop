package org.example.online_products_shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.AvailableProduct;
import org.example.online_products_shop.dto.AvailableProductDto;
import org.example.online_products_shop.service.AvailableProductService;
import org.example.online_products_shop.updatedto.AvailableProductUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availableProduct")
@RequiredArgsConstructor
@Tag(
        name = "AvailableProduct API",
        description = "Admins are in control of products"
)
public class AvailableProductController {
    private final AvailableProductService availableProductService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save available products", description = "for saving available products only shop admins")
    public ResponseEntity<AvailableProduct> save(@RequestBody AvailableProductDto availableProductDto) {
        return ResponseEntity.ok(availableProductService.save(availableProductDto));
    }

    @GetMapping("/show_all/district_name/{name}")
    @Operation(summary = "Show All available products", description = "for showing all available products by district name")
    public ResponseEntity<List<AvailableProduct>> showAll(@PathVariable("name") String districtName) {
        return ResponseEntity.ok(availableProductService.findAllAvailableProductsByDistrictName(districtName));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update available products", description = "Uses for updating available products only for shop admins")
    public ResponseEntity<AvailableProduct> update(@RequestBody AvailableProductUpdateDto updateDto) {
        return ResponseEntity.ok(availableProductService.update(updateDto));
    }
}
