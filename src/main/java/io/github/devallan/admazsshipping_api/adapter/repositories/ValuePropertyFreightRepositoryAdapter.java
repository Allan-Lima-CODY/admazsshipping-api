package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.ValuePropertyFreightEntity;
import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;
import io.github.devallan.admazsshipping_api.core.ports.ValuePropertyFreightRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValuePropertyFreightRepositoryAdapter implements ValuePropertyFreightRepositoryPort {
    private final ValuePropertyFreightRepository valuePropertyFreightRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ValuePropertyFreight> findByFreightId(Long freightId) {
        return findByFreightId(freightId, null, Pageable.unpaged());
    }

    @Override
    public List<ValuePropertyFreight> findByFreightId(Long freightId, String search, Pageable pageable) {
        Page<ValuePropertyFreightEntity> page;

        if (search == null || search.isBlank()) {
            page = valuePropertyFreightRepository.findByFreightId(freightId, pageable);
        } else {
            page = valuePropertyFreightRepository.findByFreightIdAndSearch(freightId, search, pageable);
        }

        return page.stream()
                .map(entity -> modelMapper.map(entity, ValuePropertyFreight.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteByFreightAndPropertyIds(Long freightId, List<Long> propertyIds) {
        valuePropertyFreightRepository.deleteByFreightIdAndPropertyIdIn(freightId, propertyIds);
    }

    @Override
    @Transactional
    public void updateValueByFreightAndProperty(Long freightId, Long propertyId, String value) {
        valuePropertyFreightRepository.updateValueByFreightAndProperty(freightId, propertyId, value);
    }

    @Override
    @Transactional
    public void deleteAllByFreightId(Long freightId) {
        valuePropertyFreightRepository.deleteAllByFreight_Id(freightId);
    }

    @Override
    public boolean existsByPropertyId(Long propertyId) {
        return valuePropertyFreightRepository.existsByPropertyId(propertyId);
    }
}
