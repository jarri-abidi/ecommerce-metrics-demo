package com.example.metrics.ecommerce.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException() {}
    public InvalidArgumentException(String msg) {
        super(msg);
    }
}
