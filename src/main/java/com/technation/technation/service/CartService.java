package com.technation.technation.service;

import com.technation.technation.model.Cart;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.Product;
import com.technation.technation.model.User;
import com.technation.technation.repository.CartItemRepository;
import com.technation.technation.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService{

    CartRepository cartRepo;
    CartItemRepository cartItemRepo;

    CartService(CartRepository cartRepo, CartItemRepository cartItemRepo){
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public Cart getOrCreateCartForSession(String sessionId) {
        return cartRepo.findBySessionId(sessionId).
                orElseGet(() ->{
                    Cart cart = new Cart();
                    cart.setSessionId(sessionId);
                    return cartRepo.save(cart);
                });
    }

    public Cart getOrCreateCartForUser(User user) {
        return cartRepo.findByUser(user).
                orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(user);
                    return cartRepo.save(cart);
                });
    }

    public Cart findCartBySessionId(String sessionId) {
        return cartRepo.findBySessionId(sessionId).orElse(null);
    }

    public Cart findCartByUser(User user) {
        return cartRepo.findByUser(user).orElse(null);
    }

    public Cart getCartForUser(User user) {
        return cartRepo.findByUser(user).get();
    }

    public void assignSessionCartToUser(String sessionId, User user) {
        Cart sessionCart = cartRepo.findBySessionId(sessionId).orElse(null);
        if(sessionCart != null){
            sessionCart.setUserId(user);
            sessionCart.setSessionId(null);
            cartRepo.save(sessionCart);
        }
    }

    public void mergeSessionCartWithUserCart(String sessionId, User user){
        Cart sessionCart = cartRepo.findBySessionId(sessionId).orElse(null);
        Cart userCart = cartRepo.findByUser(user).orElse(null);

        if(sessionCart != null){

            if(userCart == null){
                userCart = new Cart();
                userCart.setUserId(user);
                cartRepo.save(userCart);
            }

            for(CartItem sessionItem : sessionCart.getCartItems()){
                Optional<CartItem> existing = cartItemRepo.findByCartAndProduct(userCart, sessionItem.getProduct());

                if(existing.isPresent()){
                    CartItem userItem = existing.get();
                    userItem.setQuantity(userItem.getQuantity() + sessionItem.getQuantity());
                    cartItemRepo.save(userItem);
                }
                else{
                    sessionItem.setCart(userCart);
                    cartItemRepo.save(sessionItem);
                }
            }
            cartItemRepo.deleteAll(sessionCart.getCartItems());
            cartRepo.delete(sessionCart);
        }
    }

}
