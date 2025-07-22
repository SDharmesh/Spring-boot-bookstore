package com.dharmesh.bookstore.orderservice.jobs;

import com.dharmesh.bookstore.orderservice.domain.order.OrderEventService;
import java.time.Instant;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class OrderEventsPublishingJob {
    private final Logger logger = LoggerFactory.getLogger(OrderEventsPublishingJob.class);
    private final OrderEventService orderEventService;

    OrderEventsPublishingJob(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    @Scheduled(cron = "${orders.publish-order-events-job-cron}")
    @SchedulerLock(name = "publishOrderEvents")
    public void publishJob() {
        LockAssert.assertLocked();
        logger.info("Publishing Order Events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
