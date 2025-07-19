package com.dharmesh.bookstore.orderservice.domain.order;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException forCode(String code) {
        return new OrderNotFoundException("Product not found for code:" + code);
    }
}
