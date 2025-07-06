package com.technation.technation.dto;

import com.technation.technation.model.CarousalImg;

public class CarousalImgDTO {
    private int id;
    private String imageURL;

    public CarousalImgDTO(CarousalImg carousalImg) {
        this.id = carousalImg.getId();
        this.imageURL = carousalImg.getImageURL();
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

