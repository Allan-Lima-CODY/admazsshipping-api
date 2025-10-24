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
            throw new IllegalArgumentException("Uma propriedade '" + property.getName() + "' já existe para o seu cliente.");
        }

        if (property.getCustomer() == null) {
            throw new IllegalArgumentException("O cliente precisa ser fornecido para a propriedade.");
        }

        if (property.getName() == null || property.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da propriedade não pode ser vazio.");
        }

        if (property.getType() == null || property.getType().isBlank()) {
            throw new IllegalArgumentException("A propriedade precisa ter um tipo.");
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
            throw new IllegalArgumentException("Propriedade não encontrada.");
        }

        boolean isInUse = valuePropertyFreightRepositoryPort.existsByPropertyId(id);
        if (isInUse) {
            throw new IllegalArgumentException("Há valores associados para a propriedade.");
        }

        propertyRepositoryPort.deleteProperty(id);
    }


    @Override
    public Property updateProperty(Property partialProperty) {
        Property property = propertyRepositoryPort.findById(partialProperty.getId());
        if (property == null) {
            throw new IllegalArgumentException("Propriedade não encontrada.");
        }

        if (partialProperty.getName() == null || partialProperty.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da propriedade não pode ser vazio.");
        }

        property.setName(partialProperty.getName());

        return propertyRepositoryPort.updateProperty(property);
    }

    @Override
    public Property findById(Long id) {
        Property property = propertyRepositoryPort.findById(id);
        if (property == null) {
            throw new IllegalArgumentException("Propriedade não encontrada.");
        }
        return property;
    }
}
