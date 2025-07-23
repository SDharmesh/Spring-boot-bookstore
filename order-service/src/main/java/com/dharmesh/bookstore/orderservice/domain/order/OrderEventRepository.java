package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {
    List<OrderEventEntity> findByEventType(OrderStatus orderStatus, Sort sort);

}
