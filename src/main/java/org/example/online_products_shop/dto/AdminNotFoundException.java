package org.example.online_products_shop.dto;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long adminId) {
        super("Admin with id " + adminId + " not found");
    }
}
