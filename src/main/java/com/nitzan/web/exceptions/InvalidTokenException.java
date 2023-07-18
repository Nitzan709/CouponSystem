package com.nitzan.web.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String format) {
        super(format);
    }
}