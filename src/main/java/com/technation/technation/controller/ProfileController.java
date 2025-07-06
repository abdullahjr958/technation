package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.dto.PasswordDTO;
import com.technation.technation.dto.UserDTO;
import com.technation.technation.model.*;
import com.technation.technation.repository.*;
import com.technation.technation.service.UserService;
import com.technation.technation.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    private final AuthUtil authUtil;
    private final UserRepository userRepo;
    private final UserService userService;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final WishlistRepository wishlistRepo;
    private final WishlistItemRepository wishlistItemRepo;
    private final WishlistService wishlistService;

    @Autowired
    ProfileController(AuthUtil authUtil, UserRepository userRepo, UserService userService, OrderRepository orderRepo, OrderItemRepository orderItemRepo, WishlistRepository wishlistRepo, WishlistItemRepository wishlistItemRepo,WishlistService wishlistService){
        this.authUtil = authUtil;
        this.userRepo = userRepo;
        this.userService = userService;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.wishlistRepo = wishlistRepo;
        this.wishlistItemRepo = wishlistItemRepo;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session){

        User currentUser = authUtil.getCurrentUser();
        List<String> address = Arrays.asList(currentUser.getAddress().split("\\,"));

        List<OrderItem> tempOrderItems = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        List<Order> orders = orderRepo.findByUser(currentUser).orElse(null);
        if (!orders.isEmpty()){
            for(Order order : orders) {
                tempOrderItems = orderItemRepo.findByOrder(order);
                orderItems.addAll(tempOrderItems);
            }
        }

        List<WishlistItem> wishlistItems = null;
        wishlistService.assignSessionWishlistToUser(session.getId(), currentUser);
        Wishlist wishlist = wishlistService.getOrCreateWishlistForUser(currentUser);
        if (wishlist != null){
            wishlistItems = wishlistItemRepo.findByWishlist(wishlist).orElse(null);
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("address", address);
        model.addAttribute("passDTO", new PasswordDTO());
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("wishlistItems", wishlistItems);
        model.addAttribute("authDTO", authUtil.getAuthDTO());

        return "profile-page";
    }

    @PostMapping("/wishlist-item/remove/{itemId}")
    public String wishlistItemRemove(@PathVariable int itemId){
        wishlistItemRepo.delete(wishlistItemRepo.findById(itemId).get());
        return "redirect:/profile";
    }

    @GetMapping("/edit-personal-info")
    public String getEditInfoPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "edit-info-page";
    }

    @PostMapping("/edit-personal-info")
    public String postEditInfoPage(@ModelAttribute UserDTO editedUser){
        User currentUser = authUtil.getCurrentUser();

        if (editedUser.getName() != null && !editedUser.getName().trim().isEmpty()) {
            currentUser.setName(editedUser.getName());
        }
        if (editedUser.getEmail() != null && !editedUser.getEmail().trim().isEmpty()
                && !userService.getUserByEmail(editedUser.getEmail())) {
            currentUser.setEmail(editedUser.getEmail());
        }
        if (editedUser.getAddress() != null && !editedUser.getAddress().trim().isEmpty()) {
            currentUser.setAddress(editedUser.getAddress());
        }
        if (editedUser.getPhoneNo() != null && !editedUser.getPhoneNo().trim().isEmpty()) {
            currentUser.setPhoneNo(editedUser.getPhoneNo());
        }

        userRepo.save(currentUser);

        return "redirect:/profile";
    }

    @GetMapping("/cancel-edit")
    public String cancelEdit(){
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passDTO){
        if (passDTO.getOldPass() == null || passDTO.getNewPass() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password fields cannot be empty"));
        }
        if(passDTO.getOldPass().equals(passDTO.getNewPass())){
            return ResponseEntity.badRequest().body(Map.of("message", "New Password must be different from old one"));
        }
        return userService.checkPassword(passDTO);
    }

}