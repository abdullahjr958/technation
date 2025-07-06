package com.technation.technation.dto;

import com.technation.technation.model.SpecsImg;

public class SpecsImgDTO {
    private int id;
    private String imageURL;

    public SpecsImgDTO(SpecsImg specsImg) {
        this.id = specsImg.getId();
        this.imageURL = specsImg.getImageURL();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

