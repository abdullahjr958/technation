package com.technation.technation.repository;

import com.technation.technation.model.Cart;
import com.technation.technation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findBySessionId(String id);

    Optional<Cart> findByUser(User user);

    Cart findById(int cartId);

}
