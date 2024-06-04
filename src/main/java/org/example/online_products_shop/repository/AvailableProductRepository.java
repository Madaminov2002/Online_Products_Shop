package org.example.online_products_shop.repository;

import java.util.List;
import java.util.Optional;
import org.example.online_products_shop.domain.AvailableProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvailableProductRepository extends JpaRepository<AvailableProduct, Long> {
    @Query(nativeQuery = true, value = """
            select public.district.name,
                   public.available_products.id,
                   public.available_products.count,
                   public.available_products.shop_id,
                   public.available_products.product_id,
                   public.available_products.price
            from district
                     inner join public.shop sh on district.id = sh.district_id
                     inner join public.available_products on sh.id = available_products.shop_id
                     inner join public.products on products.id = available_products.product_id
            where district.name =:districtName
            """)
    List<AvailableProduct> findAvailableProductsByDistrictName(@Param("districtName") String name);

    @Query(nativeQuery = true, value = "select public.available_products.price from" +
            " public.available_products where available_products.product_id=:pId and available_products.shop_id=:shId")
    Double findPriceByProductId(@Param("pId") Long productId, @Param("shId") Long shopId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update available_products set count=count-1 where product_id=:pId and shop_id=:shId")
    void updateCountByProductIdAndShopID(@Param("pId") Long productId, @Param("shId") Long shopId);

    @Query(nativeQuery = true, value = """
            select public.available_products.id,
                   public.available_products.count,
                   public.available_products.shop_id,
                   public.available_products.product_id,
                   public.available_products.price
            from district
                     inner join public.shop sh on district.id = sh.district_id
                     inner join public.available_products on sh.id = available_products.shop_id
                     inner join
                 public.products on products.id = available_products.product_id
            where district.name = :dName and product_id= :pId
            """)
    Optional<AvailableProduct> getAvailableProductsForSearching(@Param("dName") String districtName, @Param("pId") Long productId);

    Optional<AvailableProduct> getAvailableProductByProductId(Long productId);
}