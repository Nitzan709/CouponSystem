package com.nitzan.web.controller;

import com.nitzan.service.CustomerService;
import com.nitzan.web.dto.ClientSession;
import com.nitzan.web.dto.CouponDto;
import com.nitzan.web.dto.CustomerDto;
import com.nitzan.web.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final Map<String, ClientSession> tokensMap;
    private static final long TIME_TO_BE_EXPIRED_IN_MILLIS = TimeUnit.MINUTES.toMillis(10);

    @GetMapping("/{token}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.ok(customerService.getCustomer(customerUuid));
    }

    @GetMapping("/all/purchased/{token}")
    public ResponseEntity<List<CouponDto>> purchasedByCustomer(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.ok().body(customerService.purchaseByCustomer(customerUuid));
    }

    @GetMapping("/all/not-purchased/{token}")
    public ResponseEntity<List<CouponDto>> unPurchasedByCustomer(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.ok().body(customerService.unPurchaseByCustomer(customerUuid));
    }

    @GetMapping("/all/expiredInWeekTime/{token}")
    public ResponseEntity<List<CouponDto>> expiredInWeekTime(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.ok().body(customerService.expiredInAWeekTime(customerUuid));
    }

    @PostMapping("/purchase/{token}/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purchase(@PathVariable String token,
                         @PathVariable UUID uuid) {
        UUID customerUuid = getUuidFromToken(token);
        customerService.purchase(customerUuid, uuid);
    }

    @PatchMapping("/update-email/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerEmail(@PathVariable String token,
                                    @RequestParam String newEmail) {
        UUID customerUuid = getUuidFromToken(token);
        customerService.updateEmail(customerUuid, newEmail);
    }

    @PatchMapping("/update-password/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerPassword(@PathVariable String token,
                                       @RequestParam String newPassword) {
        UUID customerUuid = getUuidFromToken(token);
        customerService.updatePassword(customerUuid, newPassword);
    }

    public UUID getUuidFromToken(String token) {
        ClientSession clientSession = tokensMap.get(token);
        if (clientSession == null) {
            throw new InvalidTokenException("No such token");
        }
        if ((System.currentTimeMillis() - clientSession.getLastAccessedMillis()) >
                TIME_TO_BE_EXPIRED_IN_MILLIS) {
            throw new InvalidTokenException("Token expired");
        }
        if (clientSession.getClientType() != ClientSession.CUSTOMER_TYPE) {
            throw new InvalidTokenException("Incorrect client type");
        }
        clientSession.updateLastAccessedMillis();
        return clientSession.getUuid();
    }
}
