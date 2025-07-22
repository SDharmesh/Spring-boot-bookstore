package com.dharmesh.bookstore.orderservice.web.config;

import com.dharmesh.bookstore.orderservice.ApplicationProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaHealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(KafkaHealthCheck.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @PostConstruct
    public void init() {
        logger.info("OrderService initialized - Kafka listener ready");
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        logger.info("=== APPLICATION READY - Kafka consumer should be active now ===");
    }

    @KafkaListener(topics = "new-orders-1")
    public void newOrders(String message) {
        logger.info("Kafka connection is healthy - received: {}", message);
        String s = applicationProperties.newOrdersQueue();
        System.out.println("------" + s);
    }
}
