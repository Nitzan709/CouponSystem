package com.nitzan.web.exceptions;

public class CouponDoesntBelongException extends RuntimeException {
    public CouponDoesntBelongException(String format) {
        super(format);
    }
}

