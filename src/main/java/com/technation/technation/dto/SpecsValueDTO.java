package com.technation.technation.dto;

import com.technation.technation.model.SpecsValue;

public class SpecsValueDTO {
    private int id;
    private String value;
    private String label;
    private int labelId;

    public SpecsValueDTO(SpecsValue specsValue) {
        this.id = specsValue.getId();
        this.value = specsValue.getValue();
        this.label = specsValue.getLabel() != null ? specsValue.getLabel().getLabel() : null;
        this.labelId = specsValue.getLabel() != null ? specsValue.getLabel().getId() : 0;
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }
}

