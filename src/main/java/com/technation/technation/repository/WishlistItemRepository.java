package com.technation.technation.repository;

import com.technation.technation.model.Product;
import com.technation.technation.model.User;
import com.technation.technation.model.Wishlist;
import com.technation.technation.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {
    Optional<List<WishlistItem>> findByWishlist(Wishlist wishlist);

    Optional<WishlistItem> findByWishlistAndProduct(Wishlist userWishlist, Product product);
}
