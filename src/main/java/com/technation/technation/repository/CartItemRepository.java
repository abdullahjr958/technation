package com.technation.technation.repository;

import com.technation.technation.model.Cart;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    Optional<CartItem> findByCart(Cart cart);
}
