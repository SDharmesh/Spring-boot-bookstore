package com.dharmesh.bookstore.catalog.domain.Product;

import java.math.BigDecimal;

public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
