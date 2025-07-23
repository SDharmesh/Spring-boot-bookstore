package com.dharmesh.bookstore.orderservice.web.controllers.order;

import com.dharmesh.bookstore.orderservice.AbstractIT;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderSummary;
import com.dharmesh.bookstore.orderservice.domain.order.OrderService;
import com.dharmesh.bookstore.orderservice.testData.TestDataFactory;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@Sql("/test_orders_1.sql")
class OrderControllerTest extends AbstractIT {

    @Autowired
    private OrderService orderService;

    @Nested
    class CreateOrderTest {

        @Test
        @Disabled
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
        @Disabled
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

    @Nested
    class GetOrderTest {


        @Test
        @Disabled
        void GetAllOrders() {

            ObjectMapper objectMapper = new ObjectMapper();

            String payload= """
                    {
                        "customer":{
                            "name":"dharmesh",
                            "email":"test@gmail.com",
                            "phone":"90939303"
                        },
                        "deliveryAddress":{
                            "addressLine1":"adress line 1-->23",
                            "addressLine2":"Address line 2--> 4535",
                            "city":"delhi",
                            "state":"delhi",
                            "zipCode":"110003",
                            "country":"India"
                        },
                        "items":[{
                            "code":"P100",
                            "name":"The Hunger Games",
                            "price":34.00,
                            "quantity":1
                        }
                        ]
                    }
                    """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/v1/create/test/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());

            Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(()->{
                List<OrderSummary> orderSummaries = given().contentType(ContentType.JSON)
                        .when()
                        .get("/api/v1/order-list")
                        .then()
                        .extract()
                        .body()
                        .as(new TypeRef<>() {});
                Assertions.assertEquals(1, orderSummaries.size());
            });

        }
    }


    @Test
    void shouldGetAllOrders(){

        List<OrderSummary> orderSummaries = given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/order-list")
                .then()
                .extract()
                .body()
                .as(new TypeRef<>() {});
        Assertions.assertEquals(2, orderSummaries.size());

    }

    @Nested
    @Disabled
    class GetOrderByNumber {
        @Test
        void shouldGetOrderByNumber() {
            String orderNumber = "1000";
            given().contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/{orderNumber}", orderNumber)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }
    }
}
