package com.nitzan.web.exceptions;

public class InvalidDetailsException extends RuntimeException {
    public InvalidDetailsException(String format) {
        super(format);
    }
}