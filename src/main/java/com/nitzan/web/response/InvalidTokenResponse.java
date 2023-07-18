package com.nitzan.web.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidTokenResponse {
    private static final int INVALID_TOKEN = 400;
    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static InvalidTokenResponse ofNow(String message, int responseCode) {
        return new InvalidTokenResponse(System.currentTimeMillis(), message, responseCode);
    }

    public static InvalidTokenResponse invalidToken(String message) {
        return ofNow(message, INVALID_TOKEN);
    }
}
