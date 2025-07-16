package com.dharmesh.bookstore.catalog.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dharmesh.bookstore.catalog.AbstractIT;
import com.dharmesh.bookstore.catalog.domain.Product.Product;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

class ProductControllerTest extends AbstractIT {

    @Test
    void shouldReturnProducts() {

        given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/products")
                .then()
                .statusCode(200)
                .body("totalElements", is(15))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("pageNumber", is(1));
    }

    @Test
    void shouldGetProductByCode() {
        Product product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/{code}", "P100")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(Product.class);

        assertEquals("P100", product.code());
        assertEquals("The Hunger Games", product.name());
    }
}
