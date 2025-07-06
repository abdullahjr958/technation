package com.technation.technation.repository;

import com.technation.technation.model.UnverifiedUser;
import com.technation.technation.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    Optional<VerificationCode> findByCode(String code);

    Optional<VerificationCode> findByUnverifiedUser(UnverifiedUser unverifiedUser);
}
