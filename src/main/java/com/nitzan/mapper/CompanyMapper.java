package com.nitzan.mapper;


import com.nitzan.entities.Company;
import com.nitzan.web.dto.CompanyDto;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {
    Company map(CompanyDto companyDto);

    CompanyDto map(Company company);
}
