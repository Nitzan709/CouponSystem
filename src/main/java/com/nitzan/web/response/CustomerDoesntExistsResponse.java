package com.nitzan.web.response;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDoesntExistsResponse {
    private static final int INVALID_CUSTOMER_INFO = 101;
    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static CustomerDoesntExistsResponse ofNow(String message, int responseCode) {
        return new CustomerDoesntExistsResponse(System.currentTimeMillis(), message, responseCode);
    }

    public static CustomerDoesntExistsResponse invalidCustomer(String message) {
        return ofNow(message, INVALID_CUSTOMER_INFO);
    }

}
