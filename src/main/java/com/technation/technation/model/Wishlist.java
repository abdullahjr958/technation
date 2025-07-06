package com.technation.technation.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "session_id")
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @OneToMany(mappedBy = "wishlist")
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }
}
