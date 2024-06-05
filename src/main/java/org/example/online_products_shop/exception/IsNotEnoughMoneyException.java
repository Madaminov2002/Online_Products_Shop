package org.example.online_products_shop.exception;

public class IsNotEnoughMoneyException extends RuntimeException {
    public IsNotEnoughMoneyException() {
        super("Not enough money in your card!");
    }
}
