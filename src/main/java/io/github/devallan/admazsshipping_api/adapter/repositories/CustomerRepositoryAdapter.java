package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.CustomerEntity;
import io.github.devallan.admazsshipping_api.core.domain.Customer;
import io.github.devallan.admazsshipping_api.core.ports.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer createCustomer(Customer customer) {
        CustomerEntity newCustomer = customerRepository.save(modelMapper.map(customer, CustomerEntity.class));

        return modelMapper.map(newCustomer, Customer.class);
    }

    @Override
    public Customer findCustomerById(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);

        return modelMapper.map(customer, Customer.class);
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerEntity entity = customerRepository.findByEmail(email);
        return (entity != null) ? modelMapper.map(entity, Customer.class) : null;
    }

    @Override
    public Customer findByName(String name) {
        CustomerEntity entity = customerRepository.findByName(name);
        return (entity != null) ? modelMapper.map(entity, Customer.class) : null;
    }
}
