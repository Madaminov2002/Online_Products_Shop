package org.example.online_products_shop.controller;

import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.District;
import org.example.online_products_shop.dto.DistrictDto;
import org.example.online_products_shop.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/district")
public class DistrictController {
    private final DistrictService districtService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<District> save(@RequestBody DistrictDto districtDto) {
        return ResponseEntity.ok(districtService.save(districtDto));
    }
}
