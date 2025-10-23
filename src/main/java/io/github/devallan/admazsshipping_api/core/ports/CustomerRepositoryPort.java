package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Customer;

public interface CustomerRepositoryPort {
    Customer createCustomer(Customer customer);
    Customer findCustomerById(Long id);
    Customer findByEmail(String email);
    Customer findByName(String name);
}
