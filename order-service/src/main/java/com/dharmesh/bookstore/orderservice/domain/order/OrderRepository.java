package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderEntity orderEntity = this.findByOrderNumber(orderNumber).orElseThrow();
        orderEntity.setStatus(orderStatus);
        this.save(orderEntity);
    }
}
