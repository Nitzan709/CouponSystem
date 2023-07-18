package com.nitzan.service.impl;

import com.nitzan.entities.Coupon;
import com.nitzan.entities.Customer;
import com.nitzan.mapper.CouponMapper;
import com.nitzan.mapper.CustomerMapper;
import com.nitzan.repository.CouponRepository;
import com.nitzan.repository.CustomerRepository;
import com.nitzan.service.CompanyService;
import com.nitzan.service.CustomerService;
import com.nitzan.web.dto.CouponDto;
import com.nitzan.web.dto.CustomerDto;
import com.nitzan.web.exceptions.CompanyDoesntExistsException;
import com.nitzan.web.exceptions.CustomerDoesntExistsException;
import com.nitzan.web.exceptions.InvalidCouponException;
import com.nitzan.web.exceptions.InvalidDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CustomerMapper mapper;
    private final CouponMapper couponMapper;
    private final CompanyService companyService;

    @Override
    public CustomerDto insert(CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto);
        customerRepository.save(customer);
        return mapper.map(customer);
    }

    @Override
    public CustomerDto getCustomer(UUID uuid) {
        Optional<Customer> optCustomer = customerRepository.findByUuid(uuid);
        if (optCustomer.isEmpty()) {
            throw new CustomerDoesntExistsException(String.format("Unknown customer with id %s", uuid));
        }
        Customer customer = optCustomer.get();
        return mapper.map(customer);
    }

    @Override
    public void delete(UUID uuid) {
        customerRepository.deleteByUuid(uuid);
    }

    @Override
    public void updateEmail(UUID uuid, String email) {
        if (getCustomer(uuid) != null) {
            if (email != null) {
                customerRepository.updateEmailByUuid(email, uuid);
            } else {
                throw new InvalidDetailsException("customer email can't be null");
            }
        } else {
            throw new CompanyDoesntExistsException("there is no such company");
        }

    }

    @Override
    public void updatePassword(UUID uuid, String password) {
        if (getCustomer(uuid) != null) {
            if (password != null) {
                customerRepository.updatePasswordByUuid(password, uuid);
            } else {
                throw new InvalidDetailsException("customer password has to have value, can't be null");
            }
        } else {
            throw new CompanyDoesntExistsException("there is no such company");
        }

    }

    @Transactional
    @Override
    public void purchase(UUID customerUuid, UUID couponUuid) {
        CustomerDto customerDto = getCustomer(customerUuid);
        CouponDto couponDto = companyService.getCoupon(couponUuid);
        if (couponDto.getAmount() <= 0) {
            throw new InvalidCouponException("There are no coupons left to purchase");
        }
        if (customerDto.getCoupons()
                .stream()
                .map(Coupon::getUuid)
                .anyMatch(uuid -> uuid.equals(couponUuid))) {
            throw new InvalidCouponException
                    (String.format("The coupon %s " + couponUuid + " has already been purchased by %s" + customerUuid));
        }
        customerDto.getCoupons().add(couponMapper.map(couponDto));
        couponDto.setAmount(couponDto.getAmount() - 1);
    }

    @Override
    public List<CouponDto> purchaseByCustomer(UUID uuid) {
        CustomerDto customer = getCustomer(uuid);
        return customer.getCoupons()
                .stream()
                .map(couponMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> unPurchaseByCustomer(UUID uuid) {
        List<Coupon> allCoupons = couponRepository.findAll();
        List<CouponDto> customerCoupons = purchaseByCustomer(uuid);

        return allCoupons.stream()
                .map(couponMapper::map)
                .filter(coupon -> !customerCoupons.contains(coupon))
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> expiredInAWeekTime(UUID uuid) {
        List<CouponDto> customerCoupons = purchaseByCustomer(uuid);
        long now = Instant.now().getEpochSecond();
        long weekInSeconds = 7 * 24 * 60 * 60;
        return customerCoupons.stream()
                .filter(couponDto -> couponDto.getEndDate().getTime() / 1000 > now)
                .filter(couponDto -> (couponDto.getEndDate().getTime() / 1000) - now <= weekInSeconds)
                .collect(Collectors.toList());
    }


}
