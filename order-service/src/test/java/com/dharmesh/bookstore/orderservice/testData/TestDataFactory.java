package com.dharmesh.bookstore.orderservice.testData;

import static org.instancio.Select.field;

import com.dharmesh.bookstore.orderservice.domain.order.Models.Address;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.Customer;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {
    static final List<String> VALID_COUNTRIES = List.of("India", "Germany");
    static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P100", "Product 1", new BigDecimal("25.50"), 1));
    static final Set<OrderItem> INVALID_ORDER_ITEMS =
            Set.of(new OrderItem("ABCD", "Product 1", new BigDecimal("25.50"), 1));

    public static CreatedOrderRequest createValidOrderRequest() {
        return Instancio.of(CreatedOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(CreatedOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreatedOrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreatedOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Customer::phone), "")
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(CreatedOrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreatedOrderRequest createOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(CreatedOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Address::country), "")
                .set(field(CreatedOrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreatedOrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(CreatedOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(CreatedOrderRequest::items), Set.of())
                .create();
    }
}
