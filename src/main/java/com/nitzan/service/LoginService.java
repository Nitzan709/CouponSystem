package com.nitzan.service;

import com.nitzan.web.dto.ClientSession;

public interface LoginService {
    ClientSession createSession(String email, String password, int clientType);

    String generateToken();
}
