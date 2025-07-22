package com.dharmesh.bookstore.orderservice.domain.order;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String loginUserName() {
        return "user";
    }
}
