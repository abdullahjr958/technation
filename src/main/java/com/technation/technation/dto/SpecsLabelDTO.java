package com.technation.technation.dto;

import com.technation.technation.model.SpecsLabel;

public class SpecsLabelDTO {
    private int id;
    private String label;
    private int categoryId;

    public SpecsLabelDTO(SpecsLabel specsLabel) {
        this.id = specsLabel.getId();
        this.label = specsLabel.getLabel();
        this.categoryId = specsLabel.getCategory() != null ? specsLabel.getCategory().getId() : 0;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

