package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.CustomerEntity;
import io.github.devallan.admazsshipping_api.adapter.entities.FreightEntity;
import io.github.devallan.admazsshipping_api.adapter.entities.PropertyEntity;
import io.github.devallan.admazsshipping_api.adapter.entities.ValuePropertyFreightEntity;
import io.github.devallan.admazsshipping_api.core.domain.Freight;
import io.github.devallan.admazsshipping_api.core.ports.FreightRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FreightRepositoryAdapter implements FreightRepositoryPort {
    private final FreightRepository freightRepository;
    private final ModelMapper modelMapper;

    @Override
    public Freight createFreight(Freight freight) {
        FreightEntity freightEntity = modelMapper.map(freight, FreightEntity.class);
        freightEntity.setCustomer(modelMapper.map(freight.getCustomer(), CustomerEntity.class));

        if (freight.getValues() != null && !freight.getValues().isEmpty()) {
            List<ValuePropertyFreightEntity> values = freight.getValues().stream()
                    .map(v -> {
                        ValuePropertyFreightEntity entity = new ValuePropertyFreightEntity();
                        entity.setFreight(freightEntity);
                        entity.setProperty(modelMapper.map(v.getProperty(), PropertyEntity.class));
                        entity.setValue(v.getValue());
                        return entity;
                    }).toList();

            freightEntity.setValues(values);
        }

        FreightEntity savedEntity = freightRepository.save(freightEntity);

        return modelMapper.map(savedEntity, Freight.class);
    }

    @Override
    public boolean existsByName(String name) {
        return freightRepository.existsByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public void updateFreightName(Long freightId, String name) {
        freightRepository.updateFreightName(freightId, name);
    }

    @Override
    public Freight findById(Long id) {
        FreightEntity entity = freightRepository.findById(id).orElse(null);
        return (entity != null) ? modelMapper.map(entity, Freight.class) : null;
    }

    @Override
    public void deleteFreight(Long id) {
        freightRepository.deleteById(id);
    }

    @Override
    public List<Freight> findAllByCustomer(Long customerId) {
        return freightRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(freightEntity -> modelMapper.map(freightEntity, Freight.class))
                .collect(Collectors.toList());
    }
}
