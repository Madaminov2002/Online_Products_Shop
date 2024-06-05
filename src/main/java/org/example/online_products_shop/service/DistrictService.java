package org.example.online_products_shop.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.District;
import org.example.online_products_shop.dto.DistrictDto;
import org.example.online_products_shop.repository.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public District dtoToEntity(DistrictDto districtDto) {
        return District.builder()
                .name(districtDto.getName())
                .build();
    }

    public District save(DistrictDto districtDto) {
        return districtRepository.save(dtoToEntity(districtDto));
    }

    public Optional<District> findDistrictByName(String districtName) {
        return districtRepository.findDistrictByName(districtName);
    }
}
