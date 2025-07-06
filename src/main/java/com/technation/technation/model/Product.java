package com.technation.technation.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    private int id;
    private String name;
    @Column(length = 5000)
    private String description;
    private double price;
    private int stock;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    //Mappings
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SpecsValue> value;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SpecsImg> specsImg;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CarousalImg> carousalImg;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<WishlistItem> wishlistItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<SpecsValue> getValue() {
        return value;
    }

    public void setValue(List<SpecsValue> value) {
        this.value = value;
    }

    public List<SpecsImg> getSpecsImg() {
        return specsImg;
    }

    public void setSpecsImg(List<SpecsImg> specsImg) {
        this.specsImg = specsImg;
    }

    public List<CarousalImg> getCarousalImg() {
        return carousalImg;
    }

    public void setCarousalImg(List<CarousalImg> carousalImg) {
        this.carousalImg = carousalImg;
    }

    public List<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
