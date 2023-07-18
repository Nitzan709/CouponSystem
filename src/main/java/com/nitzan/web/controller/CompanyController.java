package com.nitzan.web.controller;

import com.nitzan.service.CompanyService;
import com.nitzan.web.dto.ClientSession;
import com.nitzan.web.dto.CompanyDto;
import com.nitzan.web.dto.CouponDto;
import com.nitzan.web.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private CompanyService companyService;
    private static final long TIME_TO_BE_EXPIRED_IN_MILLIS = TimeUnit.MINUTES.toMillis(10);
    private final Map<String, ClientSession> tokensMap;

    @GetMapping("/{token}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.ok(companyService.getCompany(companyUuid));
    }

    @PostMapping("/create/{token}")
    public ResponseEntity<CouponDto> insertCoupon(@PathVariable String token,
                                                  @RequestBody CouponDto couponDto) {
        getUuidFromToken(token);
        CouponDto newCoupon = companyService.createCoupon(couponDto);
        URI couponUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("byUuid", "{id}")
                .buildAndExpand(newCoupon.getUuid())
                .toUri();
        return ResponseEntity.created(couponUri).body(newCoupon);
    }

    @DeleteMapping("/delete/{token}/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@PathVariable String token, @PathVariable  UUID uuid) {
        UUID companyUuid = getUuidFromToken(token);
        companyService.deleteCoupon(companyUuid, uuid);
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<List<CouponDto>> getAllCompanyCoupons(@PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.ok().body(companyService.getAllCompanyCoupons(companyUuid));
    }

    @PostMapping("/update-amount/{token}")
    public ResponseEntity<CouponDto> updateCouponAmount(@PathVariable String token,
                                                        @RequestParam UUID couponUuid,
                                                        @RequestParam int amount) {
        UUID companyUuid = getUuidFromToken(token);
        companyService.updateCouponAmount(companyUuid, couponUuid, amount);
        return ResponseEntity.ok().body(companyService.getCoupon(couponUuid));
    }

    @PostMapping("/update-email/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompanyEmail(@PathVariable String token,
                                   @RequestParam String newEmail) {
        UUID companyUuid = getUuidFromToken(token);
        companyService.updateEmail(companyUuid, newEmail);
    }

    @PostMapping("/update-password/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompanyPassword(@PathVariable String token,
                                      @RequestParam String newPassword) {
        UUID companyUuid = getUuidFromToken(token);
        companyService.updatePassword(companyUuid, newPassword);

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
        if (clientSession.getClientType() != ClientSession.COMPANY_TYPE) {
            throw new InvalidTokenException("Incorrect client type");
        }
        clientSession.updateLastAccessedMillis();
        return clientSession.getUuid();
    }
}