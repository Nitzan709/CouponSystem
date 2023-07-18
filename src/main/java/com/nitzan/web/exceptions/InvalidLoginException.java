package com.nitzan.web.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String format) {
        super(format);
    }
}