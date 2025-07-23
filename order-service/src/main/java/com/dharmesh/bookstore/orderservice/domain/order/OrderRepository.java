package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderEntity orderEntity = this.findByOrderNumber(orderNumber).orElseThrow();
        orderEntity.setStatus(orderStatus);
        this.save(orderEntity);
    }

    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);

    @Query(
            """
            SELECT new com.dharmesh.bookstore.orderservice.domain.order.Models.OrderSummary(o.orderNumber,o.status)
            from OrderEntity o
            where o.userName =:userName""")
    List<OrderSummary> findByUserName(String userName);
}
