package org.example.online_products_shop.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String param) {
        super("User not found by [" + param + "]");
    }
}
