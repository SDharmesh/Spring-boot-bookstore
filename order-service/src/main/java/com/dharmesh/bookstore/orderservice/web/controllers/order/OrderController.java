package com.dharmesh.bookstore.orderservice.web.controllers.order;

import com.dharmesh.bookstore.orderservice.ApplicationProperties;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderResponse;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderDto;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderSummary;
import com.dharmesh.bookstore.orderservice.domain.order.OrderNotFoundException;
import com.dharmesh.bookstore.orderservice.domain.order.OrderService;
import com.dharmesh.bookstore.orderservice.domain.order.SecurityService;
import jakarta.validation.Valid;
import java.util.List;
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
    private final SecurityService securityService;
    private final OrderService orderService;

    OrderController(
            KafkaTemplate<String, String> kafkaTemplate,
            ApplicationProperties applicationProperties,
            SecurityService securityService,
            OrderService orderService) {
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PostMapping("/create/orders")
    @ResponseStatus(HttpStatus.CREATED)
    CreatedOrderResponse createOrder(@Valid @RequestBody CreatedOrderRequest request) {

        String loginUserName = securityService.loginUserName();
        logger.info("Logged in user name is:{}", loginUserName);

        return orderService.createOrder(loginUserName, request);
    }
    @PostMapping("/create/test/orders")
    @ResponseStatus(HttpStatus.CREATED)
    CreatedOrderResponse createOrderForTest(@Valid @RequestBody CreatedOrderRequest request) {

        String loginUserName = securityService.loginUserName();
        logger.info("Logged in user name is:{}", loginUserName);

        return orderService.createOrderForTestMethod(loginUserName, request);
    }



    @GetMapping("/order-list")
    List<OrderSummary> getOrders() {
        String userName = securityService.loginUserName();
        logger.info("Logged in User Name is:{}", userName);
        return orderService.getAllOrders(userName);
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDto getOrder(@PathVariable(value = "orderNumber") String orderNumber) {
        String userName = securityService.loginUserName();
        logger.info("Logged in User Name is:{} and orderNumber is:{}", userName,orderNumber);

        return orderService
                .findByUserOrder(userName, orderNumber)
                .orElseThrow(() -> OrderNotFoundException.forOrderNumber(orderNumber));
    }
}
