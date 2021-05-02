package com.example.metrics.ecommerce.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {}

    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
