package com.dharmesh.bookstore.orderservice.web.controllers.order;

import static org.mockito.BDDMockito.given;

import com.dharmesh.bookstore.orderservice.domain.order.OrderService;
import com.dharmesh.bookstore.orderservice.domain.order.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OrderController.class)
class OrderControllerUnitTests {
    @MockitoBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityService securityService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        given(securityService.loginUserName()).willReturn("siva");
    }
}
