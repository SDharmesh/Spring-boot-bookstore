package com.dharmesh.bookstore.orderservice.domain.order;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
