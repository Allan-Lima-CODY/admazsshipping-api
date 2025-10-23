package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
    PropertyEntity findByName(String name);
    Collection<PropertyEntity> findAllByCustomerEntity_Id(Long customerId);
}
