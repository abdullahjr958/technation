package com.technation.technation.component;

import com.technation.technation.model.User;
import com.technation.technation.service.CartService;
import com.technation.technation.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private AuthUtil authUtil;
    private CartService cartService;

    @Autowired
    CustomLoginSuccessHandler(AuthUtil authUtil, CartService cartService){
        this.authUtil = authUtil;
        this.cartService = cartService;
    }

    private final RequestCache requestCache = new HttpSessionRequestCache();

    //This method is used to redirect user back to where he came from after the successful authentication
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String sessionId = request.getSession().getId();
        User user = authUtil.getCurrentUser();
        cartService.mergeSessionCartWithUserCart(sessionId, user);

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            response.sendRedirect(targetUrl);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
