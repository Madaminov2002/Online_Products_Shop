package org.example.online_products_shop.exception;

public class AvailableProductNotFoundException extends RuntimeException {
    public AvailableProductNotFoundException(Long id) {
        super("Available Product with id " + id + " not found");
    }
}
