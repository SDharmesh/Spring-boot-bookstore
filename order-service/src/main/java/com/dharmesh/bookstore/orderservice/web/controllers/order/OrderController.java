package com.dharmesh.bookstore.orderservice.web.controllers.order;

import com.dharmesh.bookstore.orderservice.ApplicationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class OrderController {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ApplicationProperties applicationProperties;

    OrderController(KafkaTemplate<String,String> kafkaTemplate,ApplicationProperties applicationProperties){
        this.kafkaTemplate=kafkaTemplate;
        this.applicationProperties=applicationProperties;
    }

    @PostMapping("/send/kafka-msg")
    ResponseEntity<String> sendTestMessage(){

        String message = "New order created successfully..";
        // Modern approach with CompletableFuture

        kafkaTemplate.send("new-orders-1", message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("=== SENT TO PARTITION: " + result.getRecordMetadata().partition() + " ===");
                        System.out.println("=== SENT TO OFFSET: " + result.getRecordMetadata().offset() + " ===");
                    } else {
                        System.out.println("Failed to send message: " + ex.getMessage());
                    }
                });

        return ResponseEntity.ok("Message send");
    }

}
