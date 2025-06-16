package com.jorgeroberto.park_api.web.dto.mapper;

import com.jorgeroberto.park_api.entities.Customer;
import com.jorgeroberto.park_api.web.dto.CustomerCreateDto;
import com.jorgeroberto.park_api.web.dto.CustomerResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {
    public static Customer toCustomer(CustomerCreateDto dto) {
        return new ModelMapper().map(dto, Customer.class);
    }

    public static CustomerResponseDto toDto(Customer customer) {
        return new ModelMapper().map(customer, CustomerResponseDto.class);
    }
}
