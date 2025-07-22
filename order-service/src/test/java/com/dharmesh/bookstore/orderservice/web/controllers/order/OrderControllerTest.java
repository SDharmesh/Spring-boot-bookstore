package com.dharmesh.bookstore.orderservice.web.controllers.order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.dharmesh.bookstore.orderservice.AbstractIT;
import com.dharmesh.bookstore.orderservice.testData.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIT {

    @Nested
    class CreateOrderTest {

        @Test
        void checkOrderIsCreated() {

            String payload =
                    """
                {
                    "customer":{
                        "name":"dharmesh",
                        "email":"test@gmail.com",
                        "phone":"90939303"
                    },
                    "deliveryAddress":{
                        "addressLine1":"Address line 1-->23",
                        "addressLine2":"Address line 2--> 4535",
                        "city":"delhi",
                        "state":"delhi",
                        "zipCode":"110003",
                        "country":"India"
                    },
                    "items":[{
                        "code":"P1000",
                        "name":"Product 1",
                        "price":25.97,
                        "quantity":1
                    }
                    ]
                }""";

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/v1/create/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/v1/create/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
