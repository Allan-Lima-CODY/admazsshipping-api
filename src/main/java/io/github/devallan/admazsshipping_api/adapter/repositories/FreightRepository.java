package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.FreightEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreightRepository extends JpaRepository<FreightEntity,Long> {
    boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("""
        UPDATE FreightEntity f
        SET f.name = :name
        WHERE f.id = :freightId
    """)
    void updateFreightName(@Param("freightId") Long freightId, @Param("name") String name);
    List<FreightEntity> findAllByCustomer_Id(Long customerId);
}
