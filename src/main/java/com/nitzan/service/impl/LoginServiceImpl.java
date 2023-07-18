package com.nitzan.service.impl;

import com.nitzan.repository.CompanyRepository;
import com.nitzan.repository.CustomerRepository;
import com.nitzan.service.LoginService;
import com.nitzan.web.dto.ClientSession;
import com.nitzan.web.exceptions.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    private static final int TOKEN_LENGTH = 8;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final Map<String, ClientSession> tokens;

    @Autowired
    public LoginServiceImpl(CustomerRepository customerRepository, CompanyRepository companyRepository, @Qualifier("tokens")
    Map<String, ClientSession> tokens) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.tokens = tokens;
    }

    @Override
    public ClientSession createSession(String email, String password, int clientType) {
        if (clientType == ClientSession.CUSTOMER_TYPE) {
            UUID customerUuid = customerRepository.findByEmailAndPassword(email, password)
                    .orElseThrow(() -> new InvalidLoginException("invalid login information, cannot login customer"))
                    .getUuid();
            isSessionExists(customerUuid);
            return ClientSession.of(customerUuid, clientType);
        }
        if (clientType == ClientSession.COMPANY_TYPE) {
            UUID companyUuid = companyRepository.findByEmailAndPassword(email, password)
                    .orElseThrow(() -> new InvalidLoginException("invalid password or email, cannot login company"))
                    .getUuid();
            isSessionExists(companyUuid);
            return ClientSession.of(companyUuid, clientType);
        }

        throw new InvalidLoginException("Unknown type: " + clientType);
    }

    private void isSessionExists(UUID clientUuid) {
        for (Map.Entry<String, ClientSession> entry : tokens.entrySet()) {
            String key = entry.getKey();
            ClientSession clientSession = entry.getValue();

            if (clientSession.getUuid().equals(clientUuid)) {
                tokens.remove(key);
                break;
            }
        }
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, TOKEN_LENGTH);
    }

}
