package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Property;

import java.util.Collection;

public interface PropertyRepositoryPort {
    Property createProperty(Property property);
    Property findByName(String name);
    Collection<Property> findAllByCustomer(Long customerId);
    void deleteProperty(Long id);
    Property findById(Long id);
    Property updateProperty(Property property);
}
