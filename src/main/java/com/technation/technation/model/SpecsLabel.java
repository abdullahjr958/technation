package com.technation.technation.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "specs_label")
public class SpecsLabel {

    @Id
    private int id;
    @Column(name = "label")
    private String label;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "label", cascade = CascadeType.ALL)
    private List<SpecsValue> specsValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<SpecsValue> getSpecsValue() {
        return specsValue;
    }

    public void setSpecsValue(List<SpecsValue> specsValue) {
        this.specsValue = specsValue;
    }
}
