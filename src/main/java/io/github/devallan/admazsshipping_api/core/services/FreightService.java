package io.github.devallan.admazsshipping_api.core.services;

import io.github.devallan.admazsshipping_api.core.domain.Freight;
import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;
import io.github.devallan.admazsshipping_api.core.ports.FreightRepositoryPort;
import io.github.devallan.admazsshipping_api.core.ports.FreightServicePort;
import io.github.devallan.admazsshipping_api.core.ports.PropertyRepositoryPort;
import io.github.devallan.admazsshipping_api.core.ports.ValuePropertyFreightRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record FreightService(FreightRepositoryPort freightRepositoryPort,
                             PropertyRepositoryPort propertyRepositoryPort,
                             ValuePropertyFreightRepositoryPort valuePropertyFreightRepositoryPort) implements FreightServicePort {
    @Override
    public Freight createFreight(Freight freight) {
        if (freight.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must be provided for the freight.");
        }

        if (freight.getCreatedAt() == null) {
            throw new IllegalArgumentException("Creation date must be provided.");
        }

        if (freight.getName() == null || freight.getName().isBlank()) {
            throw new IllegalArgumentException("Freight name must be provided.");
        }

        boolean nameExists = freightRepositoryPort.existsByName(freight.getName());
        if (nameExists) {
            throw new IllegalArgumentException("A freight with this name already exists.");
        }

        var existsProperties = propertyRepositoryPort.findAllByCustomer(freight.getCustomer().getId());
        if (existsProperties.isEmpty()) {
            throw new IllegalArgumentException("Customer has no properties configured — cannot create freight.");
        }

        if (freight.getValues() != null && !freight.getValues().isEmpty()) {
            Set<Long> uniquePropertyIds = new HashSet<>();
            for (ValuePropertyFreight value : freight.getValues()) {
                Long propertyId = (value.getProperty() != null) ? value.getProperty().getId() : null;

                if (propertyId == null) {
                    throw new IllegalArgumentException("Each value must have a valid property ID.");
                }

                if (!uniquePropertyIds.add(propertyId)) {
                    throw new IllegalArgumentException(
                            "Duplicate property detected in freight creation: property ID " + propertyId
                    );
                }
            }
        }

        return freightRepositoryPort.createFreight(freight);
    }

    @Override
    public List<ValuePropertyFreight> getFreightValues(Long freightId, String search, int page, int size) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("Invalid freight ID.");
        }

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("property.id").ascending());
        List<ValuePropertyFreight> values = valuePropertyFreightRepositoryPort.findByFreightId(freightId, search, pageable);

        if (values.isEmpty()) {
            throw new IllegalArgumentException("No property values found for this freight.");
        }

        return values;
    }

    @Override
    public void deleteFreightValues(Long freightId, List<Long> propertyIds) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("Invalid freight ID.");
        }

        if (propertyIds == null || propertyIds.isEmpty()) {
            throw new IllegalArgumentException("No property was selected to be unlinked!");
        }

        valuePropertyFreightRepositoryPort.deleteByFreightAndPropertyIds(freightId, propertyIds);
    }

    @Override
    public void updateFreightValues(Long freightId, List<ValuePropertyFreight> values) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("Invalid freight ID.");
        }

        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("No values provided for update.");
        }

        for (ValuePropertyFreight value : values) {
            if (value.getProperty() == null || value.getProperty().getId() == null) {
                throw new IllegalArgumentException("Each value must have a valid property ID.");
            }

            if (value.getValue() == null || value.getValue().isBlank()) {
                throw new IllegalArgumentException("Each value must have a non-empty value.");
            }

            valuePropertyFreightRepositoryPort.updateValueByFreightAndProperty(
                    freightId,
                    value.getProperty().getId(),
                    value.getValue()
            );
        }
    }

    @Override
    public void updateFreightName(Freight freight) {
        if (freight.getId() == null || freight.getId() <= 0) {
            throw new IllegalArgumentException("Invalid freight ID.");
        }

        if (freight.getName() == null || freight.getName().isBlank()) {
            throw new IllegalArgumentException("Freight name must be provided.");
        }

        boolean nameExists = freightRepositoryPort.existsByName(freight.getName());
        if (nameExists) {
            throw new IllegalArgumentException("A freight with this name already exists.");
        }

        freightRepositoryPort.updateFreightName(freight.getId(), freight.getName());
    }

    @Override
    public void deleteFreightAndValues(Long freightId) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("Invalid freight ID.");
        }

        Freight freight = freightRepositoryPort.findById(freightId);
        if (freight == null) {
            throw new IllegalArgumentException("Freight not found.");
        }

        valuePropertyFreightRepositoryPort.deleteAllByFreightId(freightId);

        freightRepositoryPort.deleteFreight(freightId);
    }

    @Override
    public List<Freight> findAllByCustomer(Long customerId) {
        if (customerId == null || customerId <= 0) {
            return Collections.emptyList();
        }
        List<Freight> freights = freightRepositoryPort.findAllByCustomer(customerId);
        return (freights != null) ? freights : Collections.emptyList();
    }
}