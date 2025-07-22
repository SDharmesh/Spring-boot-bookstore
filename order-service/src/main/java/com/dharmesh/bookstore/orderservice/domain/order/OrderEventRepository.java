package com.dharmesh.bookstore.orderservice.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {}
