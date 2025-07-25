package com.dharmesh.bookstore.catalog.domain.Product;

class ProductMapper {

    static Product toProduct(ProductEntity productEntity) {

        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
