package io.github.devallan.admazsshipping_api.core.domain;

public class ValuePropertyFreight {
    private Long id;
    private Freight freight;
    private Property property;
    private String value; // único campo

    public ValuePropertyFreight() {}

    public ValuePropertyFreight(Long id, Freight freight, Property property, String value) {
        this.id = id;
        this.freight = freight;
        this.property = property;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Freight getFreight() {
        return freight;
    }

    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
