package com.dharmesh.bookstore.catalog.domain.Product;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException forCode(String code) {
        return new ProductNotFoundException("Product not found for code:" + code);
    }
}
