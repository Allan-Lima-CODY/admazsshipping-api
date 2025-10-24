package io.github.devallan.admazsshipping_api.core.services;

import io.github.devallan.admazsshipping_api.core.domain.Customer;
import io.github.devallan.admazsshipping_api.core.ports.CustomerRepositoryPort;
import io.github.devallan.admazsshipping_api.core.ports.CustomerServicePort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record CustomerService(CustomerRepositoryPort customerRepositoryPort) implements CustomerServicePort {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepositoryPort.findByName(customer.getName()) != null)
            throw new IllegalArgumentException("Um cliente com o nome '" + customer.getName() + "' já existe.");

        if (customerRepositoryPort.findByEmail(customer.getEmail()) != null)
            throw new IllegalArgumentException("Um cliente com o email '" + customer.getEmail() + "' já existe.");

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerRepositoryPort.createCustomer(customer);
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepositoryPort.findCustomerById(id);
    }
}
