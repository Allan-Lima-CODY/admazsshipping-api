package io.github.devallan.admazsshipping_api.adapter.repositories;

import io.github.devallan.admazsshipping_api.adapter.entities.ValuePropertyFreightEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValuePropertyFreightRepository extends JpaRepository<ValuePropertyFreightEntity, Integer> {
    Page<ValuePropertyFreightEntity> findByFreightId(Long freightId, Pageable pageable);
    void deleteByFreightIdAndPropertyIdIn(Long freightId, List<Long> propertyIds);

    @Modifying
    @Transactional
    @Query("""
        UPDATE ValuePropertyFreightEntity v
        SET v.value = :value
        WHERE v.freight.id = :freightId AND v.property.id = :propertyId
    """)
    void updateValueByFreightAndProperty(
            @Param("freightId") Long freightId,
            @Param("propertyId") Long propertyId,
            @Param("value") String value
    );

    @Query("""
    SELECT v
    FROM ValuePropertyFreightEntity v
    WHERE v.freight.id = :freightId
      AND (
            CAST(v.id AS string) LIKE CONCAT('%', :search, '%')
         OR LOWER(v.property.name) LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(v.property.type) LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(v.value) LIKE LOWER(CONCAT('%', :search, '%'))
      )
""")
    Page<ValuePropertyFreightEntity> findByFreightIdAndSearch(
            @Param("freightId") Long freightId,
            @Param("search") String search,
            Pageable pageable
    );

    @Transactional
    void deleteAllByFreight_Id(Long freightId);
    boolean existsByPropertyId(Long propertyId);
}
