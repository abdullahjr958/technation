package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.model.*;
import com.technation.technation.repository.WishlistItemRepository;
import com.technation.technation.service.CartItemService;
import com.technation.technation.service.CartService;
import com.technation.technation.service.ProductService;
import com.technation.technation.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DetailsController {

    private final CartService cartService;
    private final ProductService prodService;
    private final CartItemService cartItemService;
    private final WishlistService wishlistService;
    private final WishlistItemRepository wishlistItemRepo;
    private final AuthUtil authUtil;

    @Autowired
    DetailsController(CartService cartService, ProductService prodService, CartItemService cartItemService, WishlistService wishlistService, WishlistItemRepository wishlistItemRepo, AuthUtil authUtil){
        this.cartService = cartService;
        this.prodService = prodService;
        this.cartItemService = cartItemService;
        this.wishlistService = wishlistService;
        this.wishlistItemRepo = wishlistItemRepo;
        this.authUtil = authUtil;
    }

    @PostMapping("/add-to-cart/{prodId}")
    @ResponseBody
    public ResponseEntity<?> addToCart(@PathVariable int prodId, @RequestBody Map<String, Object> payload, HttpSession session){

        try {
            int quantity = Integer.parseInt(payload.get("quantity").toString());

            User user = authUtil.getCurrentUser();
            Cart cart;

            if(user != null){
                cart = cartService.getCartForUser(user);
                cartItemService.addProductToCart(cart, quantity, prodService.getProductById(prodId));
            }
            else{
                cart = cartService.getOrCreateCartForSession(session.getId());
                cartItemService.addProductToCart(cart, quantity, prodService.getProductById(prodId));
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Item Successfully added to cart!");

            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Could not add item to cart."));
        }
    }

    @PostMapping("/add-to-wishlist/{prodId}")
    public ResponseEntity<?> addToWishlist(@PathVariable int prodId, HttpSession session){

        try{
            Wishlist sessionWishlist = wishlistService.getOrCreateWishlistForSession(session.getId());
            WishlistItem itemToSave = new WishlistItem();
            itemToSave.setWishlist(sessionWishlist);
            itemToSave.setProduct(prodService.getProductById(prodId));
            wishlistItemRepo.save(itemToSave);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Item successfully added to wishlist");

            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "Could not add item to wishlist"));
        }
    }

}
