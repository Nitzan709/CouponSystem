package com.nitzan.web.exceptions;

public class CompanyDoesntExistsException extends RuntimeException {
    public CompanyDoesntExistsException(String format) {
        super(format);
    }
}
