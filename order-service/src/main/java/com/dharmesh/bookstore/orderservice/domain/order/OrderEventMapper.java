package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderEventMapper {

    static OrderCreatedEvent buildOrderCreateEvent(OrderEntity orderEntity) {
        return new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                orderEntity.getOrderNumber(),
                getOrderItems(orderEntity),
                orderEntity.getCustomer(),
                orderEntity.getAddress(),
                orderEntity.getCreatedAt());
    }

    static OrderDeliveredEvent buildOrderDeliveredEvent(OrderEntity order) {
        return new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getAddress(),
                LocalDateTime.now());
    }

    static OrderCancelledEvent buildOrderCancelledEvent(OrderEntity order, String reason) {
        return new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getAddress(),
                reason,
                LocalDateTime.now());
    }

    static OrderErrorEvent buildOrderErrorEvent(OrderEntity order, String reason) {
        return new OrderErrorEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getAddress(),
                reason,
                LocalDateTime.now());
    }

    private static Set<OrderItem> getOrderItems(OrderEntity order) {
        return order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
