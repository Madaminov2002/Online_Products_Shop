package org.example.online_products_shop.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.District;
import org.example.online_products_shop.domain.Shop;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.dto.AdminNotFoundException;
import org.example.online_products_shop.dto.ShopDto;
import org.example.online_products_shop.exception.DistrictNotFoundException;
import org.example.online_products_shop.repository.DistrictRepository;
import org.example.online_products_shop.repository.ShopRepository;
import org.example.online_products_shop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final DistrictRepository districtRepository;
    private final UserRepository userRepository;

    public Shop dtoToEntity(ShopDto shopDto) {
        Optional<District> district = districtRepository.findById(shopDto.getDistrictId());

        if (district.isEmpty()) {
            throw new DistrictNotFoundException(String.valueOf(shopDto.getDistrictId()));
        }

        Optional<User> adminById = userRepository.findAdminById(shopDto.getAdminId());

        if (adminById.isEmpty()) {
            throw new AdminNotFoundException(shopDto.getAdminId());
        }
        return Shop.builder()
                .district(district.get())
                .admin(adminById.get())
                .build();
    }

    public Shop save(ShopDto shopDto) {
        return shopRepository.save(dtoToEntity(shopDto));
    }
}
