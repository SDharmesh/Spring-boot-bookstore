package com.dharmesh.bookstore.catalog.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
                "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
        })
//@Import(ContainerConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Test
    void shouldGetProducts(){

        List<ProductEntity> all = productRepository.findAll();
        assertFalse(all.isEmpty(),"It has Data..");

    }

}