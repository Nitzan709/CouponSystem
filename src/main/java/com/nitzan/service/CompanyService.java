package com.nitzan.service;

import com.nitzan.web.dto.CompanyDto;
import com.nitzan.web.dto.CouponDto;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyDto insert(CompanyDto company);

    CompanyDto getCompany(UUID uuid);

    void deleteByUuid(UUID uuid);

    void updateEmail(UUID uuid, String email);

    void updatePassword(UUID uuid, String password);

    CouponDto createCoupon(CouponDto coupon);

    CouponDto getCoupon(UUID couponUuid);

    void deleteCoupon(UUID companyUuid, UUID couponUuid);

    void updateCouponAmount(UUID companyUuid, UUID couponUuid, int amount);

    List<CouponDto> getAllCompanyCoupons(UUID companyUuid);


}
