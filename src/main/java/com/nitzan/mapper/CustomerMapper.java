package com.nitzan.mapper;

import com.nitzan.entities.Customer;
import com.nitzan.web.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer map(CustomerDto customerDto);

    CustomerDto map(Customer customer);
}
