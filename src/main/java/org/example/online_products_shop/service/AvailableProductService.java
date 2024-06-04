package org.example.online_products_shop.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.AvailableProduct;
import org.example.online_products_shop.domain.District;
import org.example.online_products_shop.domain.Product;
import org.example.online_products_shop.domain.Shop;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.dto.AvailableProductDto;
import org.example.online_products_shop.dto.SearchingDto;
import org.example.online_products_shop.exception.AvailableProductNotFoundException;
import org.example.online_products_shop.exception.DistrictNotFoundException;
import org.example.online_products_shop.exception.NoAuthorityException;
import org.example.online_products_shop.exception.ProductNotFoundException;
import org.example.online_products_shop.exception.ProductNotFoundFromAvailableException;
import org.example.online_products_shop.repository.AvailableProductRepository;
import org.example.online_products_shop.repository.DistrictRepository;
import org.example.online_products_shop.repository.ProductRepository;
import org.example.online_products_shop.repository.ShopRepository;
import org.example.online_products_shop.repository.UserRepository;
import org.example.online_products_shop.updatedto.AvailableProductUpdateDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailableProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final AvailableProductRepository availableProductRepository;
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;

    public AvailableProduct dtoToEntity(final AvailableProductDto dto) {
        Optional<Product> product = productRepository.findById(dto.getProductId());
        if (product.isEmpty()) {
            throw new ProductNotFoundException(dto.getProductId());
        }
        Optional<Shop> shop = shopRepository.findById(dto.getShopId());
        return AvailableProduct
                .builder()
                .count(dto.getCount())
                .price(dto.getPrice())
                .product(product.get())
                .shop(shop.get())
                .build();
    }

    public AvailableProduct save(final AvailableProductDto dto) {
        if (checkAdminToError(dto.getShopId())) {
            throw new NoAuthorityException();
        }
        return availableProductRepository.save(dtoToEntity(dto));
    }

    /**
     * All shop admins should be able to add information to their shops
     *
     * @param shopId
     * @return
     */
    private Boolean checkAdminToError(Long shopId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Optional<Shop> shop = shopRepository.checkShop(shopId, user.getId());
        if (shop.isEmpty()) {
            return true;
        }
        return false;

    }

    public List<AvailableProduct> findAllAvailableProductsByDistrictName(String districtName) {
        return availableProductRepository.findAvailableProductsByDistrictName(districtName);
    }

    public AvailableProduct update(final AvailableProductUpdateDto updateDto) {
        Optional<AvailableProduct> availableProduct = availableProductRepository.findById(updateDto.getId());
        if (availableProduct.isEmpty()) {
            throw new AvailableProductNotFoundException(updateDto.getId());
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        /**
         *  All shop admins should be able to update information to their shops
         */
        if (!Objects.equals(availableProduct.get().getShop().getAdmin().getId(), userRepository.findByEmail(email).getId())) {
            throw new NoAuthorityException();
        }
        if (updateDto.getCount() != null) {
            availableProduct.get().setCount(updateDto.getCount());
        }
        if (updateDto.getPrice() != null) {
            availableProduct.get().setPrice(updateDto.getPrice());
        }
        return availableProductRepository.save(availableProduct.get());
    }

    public AvailableProduct getAvailableProductForSearching(final SearchingDto searchingDto) {
        Optional<District> district = districtRepository.findDistrictByName(searchingDto.getLocationName());

        if (district.isEmpty()) {
            throw new DistrictNotFoundException(searchingDto.getLocationName());
        }

        Optional<Product> product = productRepository.findProductByName(searchingDto.getProductName());

        if (product.isEmpty()) {
            throw new ProductNotFoundException(0L);
        }
        var availableProductForSearching = availableProductRepository.getAvailableProductsForSearching(
                district.get().getName(),
                product.get().getId()
        );
        if (availableProductForSearching.isPresent()) {
            return availableProductForSearching.get();
        }

        var availableProductByProductId = availableProductRepository.getAvailableProductByProductId(product.get().getId());

        if (availableProductByProductId.isPresent()) {
            return availableProductByProductId.get();
        }
        throw new ProductNotFoundFromAvailableException(product.get().getId());
    }


}
