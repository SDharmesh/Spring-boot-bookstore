package com.dharmesh.bookstore.orderservice;

import org.springframework.boot.SpringApplication;

public class TestOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(OrderServiceApplication::main)
                .with(ConfigContainer.class)
                .run(args);
    }
}
