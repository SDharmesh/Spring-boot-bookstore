package com.dharmesh.bookstore.orderservice.domain.order;

import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderRequest;
import com.dharmesh.bookstore.orderservice.domain.order.Models.CreatedOrderResponse;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderCreatedEvent;
import com.dharmesh.bookstore.orderservice.domain.order.Models.OrderStatus;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK");
    private final OrderValidator orderValidator;
    private final OrderRepository orderRepository;
    private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreatedOrderResponse createOrder(String userName, CreatedOrderRequest request) {
        orderValidator.validate(request);
        logger.info("Validated Order request successfully..");
        OrderEntity order = OrderMapper.convertToEntity(request);
        order.setUserName(userName);
        OrderEntity saved = this.orderRepository.save(order);
        logger.info("Created Order number is:{}", saved.getOrderNumber());
        // Add Kafka logic here
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreateEvent(saved);
        orderEventService.save(orderCreatedEvent);
        return new CreatedOrderResponse(saved.getOrderNumber());
    }

    public void processNewOrders() {

        List<OrderEntity> orders = orderRepository.findByStatus(OrderStatus.NEW);
        logger.info("Found {} new orders to process", orders.size());
        for (OrderEntity order : orders) {
            this.process(order);
        }
    }

    private void process(OrderEntity order) {
        try {
            if (canBeDelivered(order)) {
                logger.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
                orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));

            } else {
                logger.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                orderEventService.save(
                        OrderEventMapper.buildOrderCancelledEvent(order, "Can't deliver to the location"));
            }
        } catch (RuntimeException e) {
            logger.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
            orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
            orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
        }
    }

    private boolean canBeDelivered(OrderEntity order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(order.getAddress().country().toUpperCase());
    }
}
