package org.example.online_products_shop.repository;

import org.example.online_products_shop.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Verification findByEmail(String email);
}