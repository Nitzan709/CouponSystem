package com.nitzan.web.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidLoginResponse {
    public static final int INVALID_LOGIN = 401;
    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static InvalidLoginResponse ofNow(String message, int responseCode) {
        return new InvalidLoginResponse(System.currentTimeMillis(), message, responseCode);
    }

    public static InvalidLoginResponse invalidLogin(String message) {
        return ofNow(message, INVALID_LOGIN);
    }
}
