package com.technation.technation.service;

import com.technation.technation.model.CustomUserDetails;
import com.technation.technation.model.User;
import com.technation.technation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("🟢 loadUserByUsername CALLED with: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No User found with email " + email));

        System.out.println("✅ User found: " + user.getEmail());
        System.out.println("🔐 Stored encoded password: " + user.getPassword());

        return new CustomUserDetails(user);
    }

}
