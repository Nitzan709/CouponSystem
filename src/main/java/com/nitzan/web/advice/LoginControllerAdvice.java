package com.nitzan.web.advice;

import com.nitzan.web.exceptions.*;
import com.nitzan.web.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginControllerAdvice {

    @ExceptionHandler({InvalidLoginException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public InvalidLoginResponse handleException(InvalidLoginException ex) {
        return InvalidLoginResponse.invalidLogin(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidTokenResponse handleInvalidToken(InvalidTokenException ex) {
        return InvalidTokenResponse.invalidToken(ex.getMessage());
    }

    @ExceptionHandler({InvalidCouponException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidCouponResponse handleCouponNameException(InvalidCouponException ex) {
        return InvalidCouponResponse.nullName(ex.getMessage());
    }

    @ExceptionHandler(CompanyDoesntExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CompanyDoesntExistsResponse handleCompanyException(CompanyDoesntExistsException ex) {
        return CompanyDoesntExistsResponse.invalidCompany(ex.getMessage());
    }

    @ExceptionHandler(CustomerDoesntExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomerDoesntExistsResponse handleCustomerException(CustomerDoesntExistsException ex) {
        return CustomerDoesntExistsResponse.invalidCustomer(ex.getMessage());
    }

    @ExceptionHandler(InvalidDetailsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InvalidDetailsResponse handleInvalidDetailsException(InvalidDetailsException ex) {
        return InvalidDetailsResponse.invalidDetails(ex.getMessage());
    }
}
