package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.dto.ShipInfoDTO;
import com.technation.technation.model.Cart;
import com.technation.technation.dto.CartItemUpdateDTO;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.User;
import com.technation.technation.repository.CartItemRepository;
import com.technation.technation.service.CartItemService;
import com.technation.technation.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CartController {

    private final CartService cartService;
    private final AuthUtil authUtil;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepo;

    @Autowired
    CartController(CartService cartService, AuthUtil authUtil, CartItemService cartItemService, CartItemRepository cartItemRepo){
        this.cartService = cartService;
        this.authUtil = authUtil;
        this.cartItemService = cartItemService;
        this.cartItemRepo = cartItemRepo;
    }

    @GetMapping("/cart")
    public String getCart(HttpSession session, Model model){
        Cart cart;
        User user = authUtil.getCurrentUser();

        if(user != null){

            cartService.mergeSessionCartWithUserCart(session.getId(), user);
            cart = cartService.getCartForUser(user);

        }
        else{
            cart = cartService.getOrCreateCartForSession(session.getId());
        }
        model.addAttribute("cart", cart);
        model.addAttribute("authDTO", authUtil.getAuthDTO());

        return "cart-page";
    }

    @GetMapping("/cart-item/delete/{itemId}")
    public String deleteProduct(@PathVariable int itemId, HttpSession session){
        User currentUser = authUtil.getCurrentUser();
        Cart cart;

        if(currentUser != null){
            cart = cartService.getCartForUser(currentUser);
        } else {
            cart = cartService.getOrCreateCartForSession(session.getId());
        }

        Optional<CartItem> optionalCartItem = cartItemRepo.findById(itemId);
        if (optionalCartItem.isPresent()) {
            CartItem item = optionalCartItem.get();

            // Remove from cart's item list
            cart.getCartItems().removeIf(i -> i.getId() == itemId);

            // Delete from database
            cartItemRepo.delete(item);
        }

        return "redirect:/cart";
    }


    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(@RequestBody CartItemUpdateDTO cartItemUpdateDTO){

        int updatedQty = cartItemService.updateQuantity(cartItemUpdateDTO.getItemId(), cartItemUpdateDTO.getQuantity());

        Map<String, Object> response = new HashMap<>();
        response.put("newQuantity", updatedQty);
        return ResponseEntity.ok(response);
    }

}

