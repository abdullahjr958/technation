package com.technation.technation.service;

import com.technation.technation.model.CartItem;
import com.technation.technation.model.Order;
import com.technation.technation.model.OrderItem;
import com.technation.technation.repository.CartItemRepository;
import com.technation.technation.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private OrderItemRepository orderItemRepo;
    private CartItemRepository cartItemRepo;

    @Autowired
    OrderItemService(OrderItemRepository orderItemRepo, CartItemRepository cartItemRepo){
        this.orderItemRepo = orderItemRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public List<OrderItem> createOrderItemList(List<CartItem> cartItemList, Order order){

        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartItem item : cartItemList){
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(item.getProduct());
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());

            cartItemRepo.delete(item);
            orderItemList.add(orderItem);
        }

        orderItemRepo.saveAll(orderItemList);

        return orderItemList;
    }
}
