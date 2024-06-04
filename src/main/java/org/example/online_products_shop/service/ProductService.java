package org.example.online_products_shop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.Product;
import org.example.online_products_shop.dto.ProductDto;
import org.example.online_products_shop.exception.ProductNotFoundException;
import org.example.online_products_shop.repository.ProductRepository;
import org.example.online_products_shop.updatedto.ProductUpdateDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private Product dtoToEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getProductName())
                .dateOfManufacture(productDto.getDateOfManufacture())
                .dateOfExpiry(productDto.getDateOfExpiry())
                .farm(productDto.getFarm())
                .build();
    }

    public Product save(ProductDto productDto) {
        return productRepository.save(dtoToEntity(productDto));
    }

    public Product productUpdate(ProductUpdateDto updateDto) {

        Product product = productRepository.findById(updateDto.getId()).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(updateDto.getId());
        }
        if (updateDto.getName() != null) {
            product.setName(updateDto.getName());
        }
        if (updateDto.getFarm() != null) {
            product.setFarm(updateDto.getFarm());
        }
        if (updateDto.getDateOfManufacture() != null) {
            product.setDateOfManufacture(updateDto.getDateOfManufacture());
        }
        if (updateDto.getDateOfExpiry() != null) {
            product.setDateOfExpiry(updateDto.getDateOfExpiry());
        }

        return productRepository.save(product);
    }
}
