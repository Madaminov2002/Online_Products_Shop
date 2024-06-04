package org.example.online_products_shop.repository;

import java.util.Optional;
import org.example.online_products_shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query(nativeQuery = true, value = "select * from shop where id=:shId and admin_id=:aId")
    Optional<Shop> checkShop(@Param("shId") Long shopId, @Param("aId") Long adminId);
}