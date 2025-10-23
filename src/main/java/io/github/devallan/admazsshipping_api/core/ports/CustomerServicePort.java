package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Customer;

public interface CustomerServicePort {
    Customer createCustomer(Customer customer);
    Customer findCustomerById(Long id);
}
