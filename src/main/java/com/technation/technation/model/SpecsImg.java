package com.technation.technation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "specs_image")
public class SpecsImg {

    @Id
    private int id;
    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
