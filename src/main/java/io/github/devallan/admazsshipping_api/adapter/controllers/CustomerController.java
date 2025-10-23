package io.github.devallan.admazsshipping_api.adapter.controllers;

import io.github.devallan.admazsshipping_api.adapter.converters.CustomerConverter;
import io.github.devallan.admazsshipping_api.adapter.dtos.CustomerDto;
import io.github.devallan.admazsshipping_api.core.ports.CustomerServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServicePort customerServicePort;
    private final CustomerConverter customerConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto.CustomerResponseDto createCustomer(@Valid @RequestBody CustomerDto.CustomerRequestDto customerRequestDto) {
        var customer = customerServicePort.createCustomer(customerConverter.toDomain(customerRequestDto));

        return customerConverter.toResponseDto(customer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto.CustomerResponseDto getCustomerById(@PathVariable Long id) {
        var customer = customerServicePort.findCustomerById(id);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + id);
        }

        return customerConverter.toResponseDto(customer);
    }
}
