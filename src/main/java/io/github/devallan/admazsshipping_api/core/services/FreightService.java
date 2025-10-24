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
            throw new IllegalArgumentException("Um usuário precisa ser fornecido para o frete.");
        }

        if (freight.getCreatedAt() == null) {
            throw new IllegalArgumentException("Uma data precisa ser fornecida.");
        }

        if (freight.getName() == null || freight.getName().isBlank()) {
            throw new IllegalArgumentException("O frete deve ter um nome.");
        }

        boolean nameExists = freightRepositoryPort.existsByName(freight.getName());
        if (nameExists) {
            throw new IllegalArgumentException("Um frete com esse nome já existe.");
        }

        var existsProperties = propertyRepositoryPort.findAllByCustomer(freight.getCustomer().getId());
        if (existsProperties.isEmpty()) {
            throw new IllegalArgumentException("Não é possível criar um frete sem ter propriedades configuradas.");
        }

        if (freight.getValues() != null && !freight.getValues().isEmpty()) {
            Set<Long> uniquePropertyIds = new HashSet<>();
            for (ValuePropertyFreight value : freight.getValues()) {
                Long propertyId = (value.getProperty() != null) ? value.getProperty().getId() : null;

                if (propertyId == null) {
                    throw new IllegalArgumentException("Os valores precisam de uma propriedade.");
                }

                if (!uniquePropertyIds.add(propertyId)) {
                    throw new IllegalArgumentException(
                            "Propriedade duplicada: " + propertyId
                    );
                }
            }
        }

        return freightRepositoryPort.createFreight(freight);
    }

    @Override
    public List<ValuePropertyFreight> getFreightValues(Long freightId, String search, int page, int size) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("ID de frete inválido.");
        }

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Parâmetro de paginação inválido");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("property.id").ascending());

        return valuePropertyFreightRepositoryPort.findByFreightId(freightId, search, pageable);
    }

    @Override
    public void deleteFreightValues(Long freightId, List<Long> propertyIds) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("ID de frete inválido.");
        }

        if (propertyIds == null || propertyIds.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma propriedade foi selecionada!");
        }

        valuePropertyFreightRepositoryPort.deleteByFreightAndPropertyIds(freightId, propertyIds);
    }

    @Override
    public void updateFreightValues(Long freightId, List<ValuePropertyFreight> values) {
        if (freightId == null || freightId <= 0) {
            throw new IllegalArgumentException("ID de frete inválido.");
        }

        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Valores não fornecidos para atualização.");
        }

        for (ValuePropertyFreight value : values) {
            if (value.getProperty() == null || value.getProperty().getId() == null) {
                throw new IllegalArgumentException("Cada valor precisa de uma propriedade.");
            }

            if (value.getValue() == null || value.getValue().isBlank()) {
                throw new IllegalArgumentException("O valor não pode ser vazio.");
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
            throw new IllegalArgumentException("ID de frete inválido.");
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
            throw new IllegalArgumentException("ID de frete inválido.");
        }

        Freight freight = freightRepositoryPort.findById(freightId);
        if (freight == null) {
            throw new IllegalArgumentException("Frete não encontrado.");
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