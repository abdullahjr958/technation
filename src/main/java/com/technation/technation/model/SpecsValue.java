package com.technation.technation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "specs_value")
public class SpecsValue {

    @Id
    private int id;
    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private SpecsLabel label;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SpecsLabel getLabel() {
        return label;
    }

    public void setLabel(SpecsLabel label) {
        this.label = label;
    }
}
