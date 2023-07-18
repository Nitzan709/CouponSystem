package com.nitzan.service;

import com.nitzan.web.dto.CouponDto;
import com.nitzan.web.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerDto insert(CustomerDto customerDto);

    CustomerDto getCustomer(UUID uuid);

    void delete(UUID uuid);

    void updateEmail(UUID uuid, String email);

    void updatePassword(UUID uuid, String password);

    void purchase(UUID uuid, UUID couponUuid);

    List<CouponDto> purchaseByCustomer(UUID uuid);

    List<CouponDto> unPurchaseByCustomer(UUID uuid);

    List<CouponDto> expiredInAWeekTime(UUID uuid);

}
