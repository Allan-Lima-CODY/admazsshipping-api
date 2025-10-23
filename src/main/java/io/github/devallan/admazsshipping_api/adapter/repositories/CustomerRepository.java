package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByEmail(String email);
    CustomerEntity findByName(String name);
}
