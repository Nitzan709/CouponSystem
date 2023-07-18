package com.nitzan.web.response;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class InvalidCouponResponse {
    private static final int NEGATIVE_PRICE = 101;
    private static final int NULL_TITLE = 102;

    private final long timestamp;
    private final String message;
    private final int responseCode;

    private static InvalidCouponResponse ofNow(String message, int code) {
        return new InvalidCouponResponse(System.currentTimeMillis(), message, code);
    }

    public static InvalidCouponResponse invalidPrice(String message) {
        return ofNow(message, NEGATIVE_PRICE);
    }

    public static InvalidCouponResponse nullName(String message) {
        return ofNow(message, NULL_TITLE);
    }
}

