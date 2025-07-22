package com.dharmesh.bookstore.orderservice.clients.catalog;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceClient.class);
    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {

        logger.info("Fetching Product for Code:{}", code);
        var product = restClient.get().uri("/api/v1/{code}", code).retrieve().body(Product.class);

        return Optional.ofNullable(product);
    }

    Optional<Product> getProductByCodeFallback(String code, Throwable t) {
        logger.info("catalog-service get product by code fallback: code:{}, Error: {} ", code, t.getMessage());
        return Optional.empty();
    }
}
