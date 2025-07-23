package com.dharmesh.bookstore.notification_service.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class OrderEventHandler {

    private final Logger logger= LoggerFactory.getLogger(OrderEventHandler.class);

    @KafkaListener(topics = "${orders.order-events-exchange}",groupId = "create-orders-group")
    void handleCreatedOrderEvents(String message){
        logger.info("Created Order is:{}",message);
    }

    @KafkaListener(topics = "${orders.order-events-exchange}",groupId = "delivered-orders-group")
    void handleDeliveredOrderEvents(String message){
        logger.info("Delivered Order is:{}",message);
    }


}
