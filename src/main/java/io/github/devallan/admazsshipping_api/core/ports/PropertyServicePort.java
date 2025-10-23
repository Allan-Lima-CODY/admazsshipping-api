package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Property;

import java.util.Collection;

public interface PropertyServicePort {
    Property createProperty(Property property);
    Collection<Property> findAllByCustomer(Long customerId);
    void deleteProperty(Long id);
    Property updateProperty(Property property);
    Property findById(Long id);
}
