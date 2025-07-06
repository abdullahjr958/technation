package com.technation.technation.service;

import com.technation.technation.dto.PasswordDTO;
import com.technation.technation.model.CustomUserDetails;
import com.technation.technation.model.User;
import com.technation.technation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Map;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private LocalDate currentDate = LocalDate.now();

    @Autowired
    UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepository;
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(currentDate);
        userRepo.save(user);
    }

    public boolean getUserByEmail(String email){
        return userRepo.findByEmail(email).isPresent();
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if(principal instanceof CustomUserDetails){
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return (User) userDetails.getUser();
            }
        }

        return null;
    }

    public User getById(int userId){
        return userRepo.findById(userId).orElse(null);
    }

    public ResponseEntity<?> checkPassword(PasswordDTO passDTO){
        User currentUser = getCurrentUser();
        String currentPass = currentUser.getPassword();

        if(passwordEncoder.matches(passDTO.getOldPass(), currentPass)){
            currentUser.setPassword(passwordEncoder.encode(passDTO.getNewPass()));
            userRepo.save(currentUser);
            return ResponseEntity.ok("Password Changed Successfully");
        }
        else {
            return ResponseEntity.badRequest().body(Map.of("message", "Wrong Password"));
        }
    }
}
