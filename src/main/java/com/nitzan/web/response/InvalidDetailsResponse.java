package com.nitzan.web.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidDetailsResponse {
    public static final int INVALID_COMPANY_DETAILS = 101;

    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static InvalidDetailsResponse ofNow(String message, int responseCode) {
        return new InvalidDetailsResponse(System.currentTimeMillis(), message, responseCode);
    }

    public static InvalidDetailsResponse invalidDetails(String message) {
        return ofNow(message, INVALID_COMPANY_DETAILS);
    }
}
