package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Freight;
import io.github.devallan.admazsshipping_api.core.domain.ValuePropertyFreight;

import java.util.List;

public interface FreightServicePort {
    Freight createFreight(Freight freight);
    List<ValuePropertyFreight> getFreightValues(Long freightId, String search, int page, int size);
    void deleteFreightValues(Long freightId, List<Long> propertyIds);
    void updateFreightValues(Long freightId, List<ValuePropertyFreight> values);
    void updateFreightName(Freight freight);
    void deleteFreightAndValues(Long freightId);
    List<Freight> findAllByCustomer(Long customerId);
}
