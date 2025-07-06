package com.technation.technation.service;

import com.technation.technation.model.User;
import com.technation.technation.model.Wishlist;
import com.technation.technation.repository.WishlistItemRepository;
import com.technation.technation.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService{

    private final WishlistRepository wishlistRepo;
    private final WishlistItemRepository wishlistItemRepo;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepo, WishlistItemRepository wishlistItemRepo){
        this.wishlistRepo = wishlistRepo;
        this.wishlistItemRepo = wishlistItemRepo;
    }

    public Wishlist getOrCreateWishlistForSession(String sessionId) {
        return wishlistRepo.findBySessionId(sessionId)
                .orElseGet(()->{
                    Wishlist wishlist = new Wishlist();
                    wishlist.setSessionId(sessionId);
                    return wishlistRepo.save(wishlist);
                });
    }

    public Wishlist getOrCreateWishlistForUser(User user) {
        return wishlistRepo.findByUser(user).orElse(null);
    }

    @Transactional
    public void assignSessionWishlistToUser(String sessionId, User user) {
        Wishlist sessionWishlist = wishlistRepo.findBySessionId(sessionId).orElse(null);
        if(sessionWishlist != null){
            sessionWishlist.setUser(user);
            sessionWishlist.setSessionId(null);
            wishlistRepo.save(sessionWishlist);
        }
    }

}
