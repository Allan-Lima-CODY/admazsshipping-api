package io.github.devallan.admazsshipping_api.core.services;

import io.github.devallan.admazsshipping_api.core.domain.Property;
import io.github.devallan.admazsshipping_api.core.ports.PropertyRepositoryPort;
import io.github.devallan.admazsshipping_api.core.ports.PropertyServicePort;
import io.github.devallan.admazsshipping_api.core.ports.ValuePropertyFreightRepositoryPort;

import java.util.Collection;
import java.util.Collections;

public record PropertyService(PropertyRepositoryPort propertyRepositoryPort,
                              ValuePropertyFreightRepositoryPort valuePropertyFreightRepositoryPort) implements PropertyServicePort {
    @Override
    public Property createProperty(Property property) {
        Property existing = propertyRepositoryPort.findByName(property.getName());
        if (existing != null && existing.getCustomer().getId().equals(property.getCustomer().getId())) {
            throw new IllegalArgumentException("A property '" + property.getName() + "' already exists for this customer.");
        }

        if (property.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must be provided for the property.");
        }

        if (property.getName() == null || property.getName().isBlank()) {
            throw new IllegalArgumentException("Property name cannot be empty.");
        }

        if (property.getType() == null || property.getType().isBlank()) {
            throw new IllegalArgumentException("Property type cannot be empty.");
        }

        return propertyRepositoryPort.createProperty(property);
    }

    @Override
    public Collection<Property> findAllByCustomer(Long customerId) {
        Collection<Property> properties = propertyRepositoryPort.findAllByCustomer(customerId);

        if (properties == null) {
            return Collections.emptyList();
        }

        return properties;
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepositoryPort.findById(id);
        if (property == null) {
            throw new IllegalArgumentException("Property not found.");
        }

        boolean isInUse = valuePropertyFreightRepositoryPort.existsByPropertyId(id);
        if (isInUse) {
            throw new IllegalArgumentException("Cannot delete property: it is associated with one or more freights.");
        }

        propertyRepositoryPort.deleteProperty(id);
    }


    @Override
    public Property updateProperty(Property partialProperty) {
        Property property = propertyRepositoryPort.findById(partialProperty.getId());
        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }

        if (partialProperty.getName() == null || partialProperty.getName().isBlank()) {
            throw new IllegalArgumentException("Property name cannot be empty.");
        }

        property.setName(partialProperty.getName());

        return propertyRepositoryPort.updateProperty(property);
    }

    @Override
    public Property findById(Long id) {
        Property property = propertyRepositoryPort.findById(id);
        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }
        return property;
    }
}
