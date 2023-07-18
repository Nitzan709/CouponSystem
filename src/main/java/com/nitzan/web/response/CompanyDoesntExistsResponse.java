package com.nitzan.web.response;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDoesntExistsResponse {
    private static final int INVALID_COMPANY = 101;

    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static CompanyDoesntExistsResponse ofNow(String message, int responseCode) {
        return new CompanyDoesntExistsResponse(System.currentTimeMillis(), message, responseCode);
    }

    public static CompanyDoesntExistsResponse invalidCompany(String message) {
        return ofNow(message, INVALID_COMPANY);
    }
}
