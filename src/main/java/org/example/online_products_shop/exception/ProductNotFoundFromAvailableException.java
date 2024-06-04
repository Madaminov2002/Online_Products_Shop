package org.example.online_products_shop.exception;

public class ProductNotFoundFromAvailableException extends RuntimeException {
    public ProductNotFoundFromAvailableException(Long id) {
        super("Product with id " + id + " not found from available");
    }
}
