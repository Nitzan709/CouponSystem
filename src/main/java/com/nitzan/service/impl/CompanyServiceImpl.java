package com.nitzan.service.impl;

import com.nitzan.entities.Company;
import com.nitzan.entities.Coupon;
import com.nitzan.mapper.CompanyMapper;
import com.nitzan.mapper.CouponMapper;
import com.nitzan.repository.CompanyRepository;
import com.nitzan.repository.CouponRepository;
import com.nitzan.service.CompanyService;
import com.nitzan.web.dto.CompanyDto;
import com.nitzan.web.dto.CouponDto;
import com.nitzan.web.exceptions.CompanyDoesntExistsException;
import com.nitzan.web.exceptions.CouponDoesntBelongException;
import com.nitzan.web.exceptions.InvalidCouponException;
import com.nitzan.web.exceptions.InvalidDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CompanyMapper companyMapper;
    private final CouponMapper couponMapper;

    @Override
    public CompanyDto insert(CompanyDto companyDto) {
        Company company = companyMapper.map(companyDto);
        Company savedCompany = companyRepository.save(company);
        return companyMapper.map(savedCompany);
    }

    @Override
    public CompanyDto getCompany(UUID uuid) {
        Optional<Company> optCompany = companyRepository.findByUuid(uuid);
        if (optCompany.isEmpty()) {
            throw new CompanyDoesntExistsException(String.format("There is no company with id %s", uuid));
        }
        Company company = optCompany.get();
        return companyMapper.map(company);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        companyRepository.deleteByUuid(uuid);
    }

    @Override
    public void updateEmail(UUID uuid, String email) {
        if (getCompany(uuid) != null) {
            if (email != null) {
                String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                if (email.matches(emailRegex)) {
                    companyRepository.updateEmailByUuid(email, uuid);
                } else {
                    throw new InvalidDetailsException("Invalid email format");
                }
            } else {
                throw new InvalidDetailsException("Company email can't be empty");
            }
        } else {
            throw new CompanyDoesntExistsException("This company doesn't exist");
        }
    }

    @Override
    public void updatePassword(UUID uuid, String password) {
        if (getCompany(uuid) != null) {
            if (password != null) {
                companyRepository.updatePasswordByUuid(password, uuid);
            } else {
                throw new InvalidDetailsException("company password can not be null, has to have values");
            }
        } else {
            throw new CompanyDoesntExistsException("This company doesn't exists");
        }

    }

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        if (couponDto.getPrice() == null
                || couponDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidCouponException("Coupon must have price > 0");
        }
        if (couponDto.getTitle() == null) {
            throw new InvalidCouponException("Coupon must have a title");
        }
        if (couponDto.getAmount() > 1) {
            couponDto.setAmount(couponDto.getAmount() + 1);
        }
        Coupon coupon = couponMapper.map(couponDto);
        return couponMapper.map(couponRepository.save(coupon));
    }

    @Override
    public void deleteCoupon(UUID companyUuid, UUID couponUuid) {
        if (!couponBelongToCompany(companyUuid, couponUuid)) {
            throw new CompanyDoesntExistsException("excess denied you're not allowed to delete the coupon");
        }
        couponRepository.deleteByUuid(couponUuid);
    }

    @Override
    public void updateCouponAmount(UUID companyUuid, UUID couponUuid, int amount) {
        if (couponBelongToCompany(companyUuid, couponUuid)) {
            throw new CouponDoesntBelongException("the coupon doesn't belong to the company");
        }
        CouponDto coupon = getCoupon(couponUuid);
        coupon.setAmount(coupon.getAmount() + amount);
        couponRepository.save(couponMapper.map(coupon));
    }

    @Override
    public List<CouponDto> getAllCompanyCoupons(UUID companyUuid) {
        CompanyDto companyDto = getCompany(companyUuid);
        Company company = companyMapper.map(companyDto);
        return company.getCoupons()
                .stream()
                .map(couponMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CouponDto getCoupon(UUID couponUuid) {
        Optional<Coupon> optCoupon = couponRepository.findByUuid(couponUuid);
        if (optCoupon.isEmpty()) {
            throw new InvalidCouponException(String.format("This coupon doesn't exits %s", couponUuid));
        }
        return couponMapper.map(optCoupon.get());
    }

    public boolean couponBelongToCompany(UUID companyUuid, UUID couponUuid) {
        CouponDto couponDto = getCoupon(couponUuid);
        Coupon coupon = couponMapper.map(couponDto);
        return !coupon.getCompany().getUuid().equals(companyUuid);
    }

}
