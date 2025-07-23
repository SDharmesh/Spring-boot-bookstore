package com.dharmesh.bookstore.orderservice.domain.order.Models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreatedOrderRequest(
        @NotEmpty(message = "items cannot be empty") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress) {

}
