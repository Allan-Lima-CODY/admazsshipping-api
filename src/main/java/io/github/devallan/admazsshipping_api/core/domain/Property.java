package io.github.devallan.admazsshipping_api.core.domain;

public class Property {
    private Long id;
    private Customer customer;
    private String Name;
    private String Type;

    public Property() {
    }

    public Property(Long id, Customer customer, String name, String type) {
        this.id = id;
        this.customer = customer;
        Name = name;
        Type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
