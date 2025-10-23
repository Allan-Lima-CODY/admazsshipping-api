package io.github.devallan.admazsshipping_api.adapter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ValuePropertyFreight")
public class ValuePropertyFreightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FreightId", nullable = false)
    private FreightEntity freight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyId", nullable = false)
    private PropertyEntity property;

    @Column(name = "\"VALUE\"", nullable = false)
    private String value;
}
