package com.technation.technation.repository;

import com.technation.technation.model.User;
import com.technation.technation.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    Optional<Wishlist> findBySessionId(String sessionId);

    Optional<Wishlist> findByUser(User user);
}
