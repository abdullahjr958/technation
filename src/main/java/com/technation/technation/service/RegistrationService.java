package com.technation.technation.service;

import com.technation.technation.model.UnverifiedUser;
import com.technation.technation.model.VerificationCode;
import com.technation.technation.repository.UnverifiedUserRepository;
import com.technation.technation.repository.UserRepository;
import com.technation.technation.repository.VerificationCodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class RegistrationService {
    private final UnverifiedUserRepository unverifiedUserRepo;
    private final VerificationCodeRepository verificationCodeRepo;
    private final UserRepository userRepo; // your main user table
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UnverifiedUserRepository unverifiedUserRepo, VerificationCodeRepository verificationCodeRepo, UserRepository userRepo, UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.unverifiedUserRepo = unverifiedUserRepo;
        this.verificationCodeRepo = verificationCodeRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(UnverifiedUser unverifiedUser){

        // Checks if Email already exists
        boolean emailExists = userService.getUserByEmail(unverifiedUser.getEmail());
        if(emailExists){
            return ResponseEntity.badRequest().body(Map.of("message", "Email Already Exists."));
        }

        String otp = String.format("%06d", new Random().nextInt(1000000));

        unverifiedUser.setPassword(passwordEncoder.encode(unverifiedUser.getPassword()));
        unverifiedUserRepo.save(unverifiedUser);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(otp);
        verificationCode.setUnverifiedUser(unverifiedUser);
        verificationCodeRepo.save(verificationCode);

        emailService.sendOtpEmail(unverifiedUser, otp);

        return ResponseEntity.ok(Map.of("email", unverifiedUser.getEmail()));
    }

    public ResponseEntity<?> resendOtp(String email) {
        Optional<UnverifiedUser> optionalUnverifiedUser = unverifiedUserRepo.findByEmail(email);

        //Checking if there is an unverified user in the DB with the email
        if(optionalUnverifiedUser.isPresent()){
            String otp = String.format("%06d", new Random().nextInt(1000000));

            VerificationCode newCode = verificationCodeRepo.findByUnverifiedUser(optionalUnverifiedUser.get()).orElse(null);
            newCode.setCode(otp);
            newCode.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            verificationCodeRepo.save(newCode);

            emailService.sendOtpEmail(optionalUnverifiedUser.get(), otp);

            return ResponseEntity.ok(Map.of("message", "Code Resent"));
        }
        else {
            return ResponseEntity.badRequest().body(Map.of("message", "Something went wrong. Please try again later."));
        }
    }
}
