package com.technation.technation.service;

import com.technation.technation.model.Cart;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.Order;
import com.technation.technation.model.User;
import com.technation.technation.repository.CartRepository;
import com.technation.technation.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepo;
    private final CartRepository cartRepo;

    OrderService(OrderRepository orderRepo, CartRepository cartRepo){
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    @Transactional
    public void cleanCart(Cart userCart) {
        Cart managedCart = cartRepo.findById(userCart.getId());

        // This ensures orphanRemoval gets triggered
        managedCart.getCartItems().clear();

        cartRepo.delete(managedCart);
    }

    public Order placeOrder(String name, String address, User user){
        Order order = new Order();
        order.setName(name);
        order.setAddress(address);
        order.setUser(user);
        order.setStatus("Processing");

        orderRepo.save(order);
        return order;
    }

    public double calTotalAmount(List<CartItem> cartItemList){
        double totalAmount = 0;

        for(CartItem item : cartItemList){
            double price = item.getProduct().getPrice();
            double quantity = item.getQuantity();
            totalAmount += price * quantity;
        }

        return totalAmount;
    }
}
