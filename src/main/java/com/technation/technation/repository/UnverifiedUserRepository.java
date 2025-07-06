package com.technation.technation.repository;

import com.technation.technation.model.UnverifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnverifiedUserRepository extends JpaRepository<UnverifiedUser, Integer> {
    Optional<UnverifiedUser> findByEmail(String email);
}
