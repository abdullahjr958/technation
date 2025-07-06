package com.technation.technation.service;

import com.technation.technation.model.Cart;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.Product;
import com.technation.technation.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService{

    CartService cartService;
    CartItemRepository cartItemRepo;

    @Autowired
    CartItemService(CartService cartService, CartItemRepository cartItemRepo){
        this.cartService = cartService;
        this.cartItemRepo = cartItemRepo;
    }


    public void addProductToCart(Cart cart, int quantity, Product product) {

        Optional<CartItem> optionalItem = cartItemRepo.findByCartAndProduct(cart, product);

        if(optionalItem.isPresent()){
            CartItem existingItem = optionalItem.get();

            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepo.save(existingItem);
        }
        else{
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            cartItemRepo.save(cartItem);
        }

    }

    public int updateQuantity(int id, int updatedQuantity){
        Optional<CartItem> optionalItem = cartItemRepo.findById(id);

        if(optionalItem.isPresent()){
            CartItem updatedItem = optionalItem.get();
            updatedItem.setQuantity(updatedQuantity);
            cartItemRepo.save(updatedItem);
            return updatedQuantity;
        }
        else {
            throw new EntityNotFoundException("CartItem with Id " + id + " not found.");
        }
    }

}
