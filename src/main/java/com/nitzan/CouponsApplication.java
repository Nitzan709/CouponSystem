package com.nitzan;

import com.nitzan.web.dto.ClientSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CouponsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponsApplication.class, args);
    }

    @Bean(name = "tokens")
    public Map<String, ClientSession> tokensMap() {
        return new HashMap<>();
    }

}
