package com.nitzan.web.exceptions;

public class CustomerDoesntExistsException extends RuntimeException {
    public CustomerDoesntExistsException(String format) {
        super(format);
    }
}