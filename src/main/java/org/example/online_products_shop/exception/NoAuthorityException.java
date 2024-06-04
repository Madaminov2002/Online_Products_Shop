package org.example.online_products_shop.exception;

public class NoAuthorityException extends RuntimeException {
    public NoAuthorityException() {
        super("You do not have the authority to add information to this shop.");
    }
}
