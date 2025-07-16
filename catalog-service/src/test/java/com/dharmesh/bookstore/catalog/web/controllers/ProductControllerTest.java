package com.dharmesh.bookstore.catalog.web.controllers;

import com.dharmesh.bookstore.catalog.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


class ProductControllerTest extends AbstractIT {

    @Test
    void shouldReturnProducts(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/products")
                .then()
                .statusCode(200)
                .body("totalElements", is(15))
                .body("isFirst",is(true))
                .body("isLast",is(false))
                .body("pageNumber",is(1));

    }

}