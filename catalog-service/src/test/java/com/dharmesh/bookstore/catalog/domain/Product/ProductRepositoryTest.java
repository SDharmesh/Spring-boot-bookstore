package com.dharmesh.bookstore.catalog.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(
        properties = {"spring.test.database.replace=none", "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"})
// @Import(ContainerConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetProducts() {

        List<ProductEntity> all = productRepository.findAll();
        assertFalse(all.isEmpty(), "It has Data..");
    }

    @Test
    void shouldGetProductCode() {

        String code = "P100";
        Optional<ProductEntity> entity = productRepository.findByCode(code);

        assertFalse(entity.isEmpty(), "Product Found By Code");
    }

    @Test
    void shouldNotGetProductByCode() {

        String code = "P1001";
        Optional<ProductEntity> entity = productRepository.findByCode(code);

        assertFalse(entity.isPresent(), "Product Not Found");
    }
}
