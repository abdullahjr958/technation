package com.technation.technation.dto;

import com.technation.technation.model.User;

public class ShipInfoDTO {

    private String name;
    private String address;

    public ShipInfoDTO(){}

    public ShipInfoDTO(User user){
        this.name = user.getName();
        this.address = user.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
