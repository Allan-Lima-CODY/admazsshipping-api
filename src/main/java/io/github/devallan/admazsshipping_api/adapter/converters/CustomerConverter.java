package io.github.devallan.admazsshipping_api.adapter.converters;

import io.github.devallan.admazsshipping_api.adapter.dtos.CustomerDto;
import io.github.devallan.admazsshipping_api.core.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public Customer toDomain(CustomerDto.CustomerRequestDto dto) {
        return new Customer(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }

    public CustomerDto.CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerDto.CustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}