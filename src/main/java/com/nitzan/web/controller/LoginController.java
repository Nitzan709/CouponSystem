package com.nitzan.web.controller;

import com.nitzan.service.LoginService;
import com.nitzan.web.dto.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;
    private final Map<String, ClientSession> tokens;

    @Autowired
    public LoginController(LoginService loginService, @Qualifier("tokens") Map<String, ClientSession> tokens) {
        this.loginService = loginService;
        this.tokens = tokens;
    }

    @PostMapping("/login")
    public ResponseEntity<String> performLogin(@RequestBody UserCredentials credentials, @RequestParam int clientType) {
        return getStringResponseEntity(credentials, clientType);
    }

    private ResponseEntity<String> getStringResponseEntity(@RequestBody UserCredentials credentials, @RequestParam int clientType) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        String token = loginService.generateToken();
        ClientSession session = loginService.createSession(email, password, clientType);

        tokens.put(token, session);
        return ResponseEntity.ok(token);
    }
}
