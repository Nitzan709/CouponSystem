package com.nitzan.web.exceptions;

public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException(String format) {
        super(format);
    }
}