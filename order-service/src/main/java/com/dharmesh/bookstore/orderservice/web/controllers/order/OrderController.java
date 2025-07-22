package com.dharmesh.bookstore.orderservice.web.controllers.order;

import com.dharmesh.bookstore.orderservice.ApplicationProperties;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderResponse;
import com.dharmesh.bookstore.orderservice.domain.order.OrderService;
import com.dharmesh.bookstore.orderservice.domain.order.SecurityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ApplicationProperties applicationProperties;
    private final SecurityService securityService;
    private final OrderService orderService;

    OrderController(
            KafkaTemplate<String, String> kafkaTemplate,
            ApplicationProperties applicationProperties,
            SecurityService securityService,
            OrderService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.applicationProperties = applicationProperties;
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PostMapping("/send/kafka-msg")
    ResponseEntity<String> sendTestMessage() {

        String message = "New order created successfully..";
        String topic = applicationProperties.newOrdersQueue();

        kafkaTemplate.send(topic, message);

        return ResponseEntity.ok("Message send");
    }

    @PostMapping("/create/orders")
    @ResponseStatus(HttpStatus.CREATED)
    CreatedOrderResponse createOrder(@Valid @RequestBody CreatedOrderRequest request) {

        String loginUserName = securityService.loginUserName();
        logger.info("Logged in user name is:{}", loginUserName);

        return orderService.createOrder(loginUserName, request);
    }
}
