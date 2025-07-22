package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.ApplicationProperties;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderCancelledEvent;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderCreatedEvent;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderDeliveredEvent;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderErrorEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ApplicationProperties properties;

    OrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ApplicationProperties properties) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderCreatedEvent event) {
        this.send(properties.newOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        this.send(properties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        this.send(properties.errorOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        this.send(properties.deliveredOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        kafkaTemplate.send(properties.orderEventsExchange(), routingKey, payload.toString());
    }
}
