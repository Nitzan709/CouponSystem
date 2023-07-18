package com.nitzan.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCredentials {
    private final String email;
    private final String password;
}
