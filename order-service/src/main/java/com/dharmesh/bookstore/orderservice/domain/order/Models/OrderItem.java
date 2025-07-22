package com.dharmesh.bookstore.orderservice.domain.order.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Code is required") String code,
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "price is required") BigDecimal price,
        @NotNull(message = "Quantity is required") @Min(value = 1, message = "Min 1 quantity is required")
                Integer quantity) {}
