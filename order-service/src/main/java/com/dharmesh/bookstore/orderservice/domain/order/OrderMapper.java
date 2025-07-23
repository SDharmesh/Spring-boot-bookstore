package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderDto;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderItem;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {

    public static OrderEntity convertToEntity(CreatedOrderRequest request) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(UUID.randomUUID().toString());
        orderEntity.setStatus(OrderStatus.NEW);
        orderEntity.setCustomer(request.customer());
        orderEntity.setAddress(request.deliveryAddress());

        Set<OrderItemEntity> orderItems = new HashSet<>();

        for (OrderItem item : request.items()) {
            OrderItemEntity itemEntity = new OrderItemEntity();

            itemEntity.setCode(item.code());
            itemEntity.setName(item.name());
            itemEntity.setPrice(item.price());
            itemEntity.setQuantity(item.quantity());
            itemEntity.setOrder(orderEntity);

            orderItems.add(itemEntity);
        }
        orderEntity.setItems(orderItems);
        return orderEntity;
    }

    static OrderDto convertToDTO(OrderEntity order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDto(
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }
}
