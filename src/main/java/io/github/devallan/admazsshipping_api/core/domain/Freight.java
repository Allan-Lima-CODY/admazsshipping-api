package io.github.devallan.admazsshipping_api.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Freight {
    private Long id;
    private String Name;
    private Customer customer;
    private LocalDateTime createdAt;
    private List<ValuePropertyFreight> values;

    public Freight() {
    }

    public Freight(Long id, String name, Customer customer, LocalDateTime createdAt, List<ValuePropertyFreight> values) {
        this.id = id;
        this.Name = name;
        this.customer = customer;
        this.createdAt = createdAt;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ValuePropertyFreight> getValues() {
        return values;
    }

    public void setValues(List<ValuePropertyFreight> values) {
        this.values = values;
    }
}
