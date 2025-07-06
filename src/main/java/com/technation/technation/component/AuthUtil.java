package com.technation.technation.component;

import com.technation.technation.dto.AuthDTO;
import com.technation.technation.model.CustomUserDetails;
import com.technation.technation.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUser();
            }
        }
        return null;
    }

    public AuthDTO getAuthDTO(){
        User currentUser = getCurrentUser();
        AuthDTO authDTO = new AuthDTO();
        if(currentUser != null){
            authDTO.setName(currentUser.getName());
            authDTO.setAuthenticated(true);
        }
        else {
            authDTO.setAuthenticated(false);
        }
        return authDTO;
    }
}

