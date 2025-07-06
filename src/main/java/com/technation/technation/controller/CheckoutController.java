package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.dto.ShipInfoDTO;
import com.technation.technation.model.*;
import com.technation.technation.repository.CartItemRepository;
import com.technation.technation.repository.CartRepository;
import com.technation.technation.service.CartService;
import com.technation.technation.service.EmailService;
import com.technation.technation.service.OrderItemService;
import com.technation.technation.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final CartRepository cartRepo;
    private final AuthUtil authUtil;
    private final CartItemRepository cartItemRepo;
    private final EmailService emailService;

    @Autowired
    CheckoutController(CartService cartService, OrderService orderService, OrderItemService orderItemService, CartRepository cartRepo, AuthUtil authUtil, CartItemRepository cartItemRepo, EmailService emailService){
        this.cartService = cartService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.cartRepo = cartRepo;
        this.authUtil = authUtil;
        this.cartItemRepo = cartItemRepo;
        this.emailService = emailService;
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model, HttpSession session){
        User currentUser = authUtil.getCurrentUser();
        Cart userCart;

        if(currentUser != null) { userCart = cartService.getCartForUser(currentUser); }
        else { userCart = cartService.getOrCreateCartForSession(session.getId()); }
        List<CartItem> cartItemList = userCart.getCartItems();

        model.addAttribute("shipInfoDTO", new ShipInfoDTO(currentUser));
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("totalAmount", orderService.calTotalAmount(cartItemList));
        model.addAttribute("totalAmountWithShipping", orderService.calTotalAmount(cartItemList) + 9.99);

        return "checkout-page";
    }

    @GetMapping("/place-order")
    public String placeOrder(@ModelAttribute ShipInfoDTO shipInfoDTO, Model model){

        User currentUser = authUtil.getCurrentUser();
        Cart userCart = cartService.getCartForUser(currentUser);

        List<CartItem> cartItemList = userCart.getCartItems();
        double totalOrderAmount = orderService.calTotalAmount(cartItemList);

        Order order = orderService.placeOrder(shipInfoDTO.getName(), shipInfoDTO.getAddress(), currentUser);
        order.setOrderNo(String.format("%06d", order.getId()));
        List<OrderItem> orderItemList = orderItemService.createOrderItemList(cartItemList, order);
        //Delete CartItems & Cart
        orderService.cleanCart(userCart);
        emailService.orderConfirmationEmail(currentUser, order, totalOrderAmount+9.99);


        model.addAttribute("order", order);
        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("totalPrice", totalOrderAmount);
        model.addAttribute("totalWithShippingPrice", totalOrderAmount+9.99);

        return "order-confirmation-page";
    }
}
