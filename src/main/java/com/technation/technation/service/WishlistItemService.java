package com.technation.technation.service;

import com.technation.technation.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistItemService {

    private WishlistItemRepository wishlistItemRepo;

    WishlistItemService(WishlistItemRepository wishlistItemRepo){
        this.wishlistItemRepo = wishlistItemRepo;
    }

    public void deleteItem(int wishlistItemId){
        wishlistItemRepo.findById(wishlistItemId).ifPresent(wishlistItemRepo::delete);
    }

}
