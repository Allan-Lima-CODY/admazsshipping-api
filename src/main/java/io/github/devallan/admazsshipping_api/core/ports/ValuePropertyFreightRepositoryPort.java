package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ValuePropertyFreightRepositoryPort {
    List<ValuePropertyFreight> findByFreightId(Long freightId);
    List<ValuePropertyFreight> findByFreightId(Long freightId, String search, Pageable pageable);
    void deleteByFreightAndPropertyIds(Long freightId, List<Long> propertyIds);
    void updateValueByFreightAndProperty(Long freightId, Long propertyId, String value);
    void deleteAllByFreightId(Long freightId);
    boolean existsByPropertyId(Long propertyId);
}
