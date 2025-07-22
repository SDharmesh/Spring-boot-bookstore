package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderItem;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
}
