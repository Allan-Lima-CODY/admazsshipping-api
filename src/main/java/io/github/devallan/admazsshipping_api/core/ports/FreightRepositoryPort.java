package io.github.devallan.admazsshipping_api.core.ports;

import io.github.devallan.admazsshipping_api.core.domain.Freight;

import java.util.List;

public interface FreightRepositoryPort {
    Freight createFreight(Freight freight);
    boolean existsByName(String name);
    void updateFreightName(Long freightId, String name);
    Freight findById(Long id);
    void deleteFreight(Long id);
    List<Freight> findAllByCustomer(Long customerId);
}
