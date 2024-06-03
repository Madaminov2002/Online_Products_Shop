package org.example.online_products_shop.repository;

import org.example.online_products_shop.domain.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
}