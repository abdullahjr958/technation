package com.technation.technation.service;

import com.technation.technation.model.UnverifiedUser;
import com.technation.technation.model.User;
import com.technation.technation.model.VerificationCode;
import com.technation.technation.repository.UnverifiedUserRepository;
import com.technation.technation.repository.UserRepository;
import com.technation.technation.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VerificationService {

    private final VerificationCodeRepository verificationCodeRepo;
    private final UnverifiedUserRepository unverifiedUserRepo;
    private final UserRepository userRepo;

    @Autowired
    VerificationService(VerificationCodeRepository verificationCodeRepo, UnverifiedUserRepository unverifiedUserRepo, UserRepository userRepo){
        this.verificationCodeRepo = verificationCodeRepo;
        this.unverifiedUserRepo = unverifiedUserRepo;
        this.userRepo = userRepo;
    }


    public boolean verifyCode(String email, String code){

        UnverifiedUser unverifiedUser = unverifiedUserRepo.findByEmail(email).orElse(new UnverifiedUser());
        Optional<VerificationCode> optionalCode = verificationCodeRepo.findByUnverifiedUser(unverifiedUser);

        //Checking if optionalCode is null
        if (optionalCode.isEmpty()) {
            unverifiedUserRepo.delete(unverifiedUser);
            return false;
        }
        //Storing the VerificationCode Object retrieved from DB
        VerificationCode verificationCode = optionalCode.get();

        // 1. Check if OTP matches
        if(!verificationCode.getCode().equals(code)) {
            cleanUp(unverifiedUser, verificationCode);
            return false;
        }

        // 2. Check Expiry
        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            cleanUp(unverifiedUser, verificationCode);
            return false;
        }

        // 3. Create Authenticated user
        User verifiedUser = new User();
        verifiedUser.setName(unverifiedUser.getName());
        verifiedUser.setEmail(unverifiedUser.getEmail());
        verifiedUser.setPassword(unverifiedUser.getPassword());
        verifiedUser.setAddress(unverifiedUser.getAddress());
        verifiedUser.setPhoneNo(unverifiedUser.getPhoneNo());

        userRepo.save(verifiedUser);

        // 4. Clean up
        cleanUp(unverifiedUser, verificationCode);

        return true;
    }

    private void cleanUp(UnverifiedUser unverifiedUser, VerificationCode verificationCode){
        unverifiedUserRepo.delete(unverifiedUser);
        verificationCodeRepo.delete(verificationCode);
    }
}
