package com.dharmesh.bookstore.catalog;

import org.springframework.boot.SpringApplication;

public class TestCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main)
                .with(ConfigContainer.class)
                .run(args);
    }
}
