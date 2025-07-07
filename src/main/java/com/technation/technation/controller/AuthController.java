package com.technation.technation.controller;

import com.technation.technation.dto.EmailRequestDTO;
import com.technation.technation.dto.OtpRequestDTO;
import com.technation.technation.model.UnverifiedUser;
import com.technation.technation.model.User;
import com.technation.technation.repository.UnverifiedUserRepository;
import com.technation.technation.service.RegistrationService;
import com.technation.technation.service.UserService;
import com.technation.technation.service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final VerificationService verificationService;
    private final UnverifiedUserRepository unverifiedUserRepo;
    private String rawPassword;

    @Autowired
    AuthController(UserService userService, AuthenticationManager authenticationManager, RegistrationService registrationService, VerificationService verificationService, UnverifiedUserRepository unverifiedUserRepo){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
        this.verificationService = verificationService;
        this.unverifiedUserRepo = unverifiedUserRepo;
    }


    @GetMapping("/login")
    public String authenticationForm(Model model){
        model.addAttribute("user", new User());
        return "login-page";
    }

    @GetMapping("/signup")
    public String showForm(Model model){
        model.addAttribute("userObj", new User());
        return "signup-page";
    }


    //Will be called when "Create Account" button is clicked
    @PostMapping("/auth/send-otp")
    @ResponseBody
    public ResponseEntity<Map<String, String>> verifyByOTP(@RequestBody UnverifiedUser unverifiedUser){
        rawPassword = unverifiedUser.getPassword();
        return registrationService.registerUser(unverifiedUser);
    }

    @PostMapping("auth/resend-otp")
    @ResponseBody
    public ResponseEntity<?> resendOtp(@RequestBody EmailRequestDTO emailRequest){
        return registrationService.resendOtp(emailRequest.getEmail());
    }

    //Will be called when "Verify Code" button is clicked
    @PostMapping("/auth/verify-otp")
    @ResponseBody
    public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody OtpRequestDTO otpRequest, HttpServletRequest request){
        if(!verificationService.verifyCode(otpRequest.getEmail(), otpRequest.getOtp())){
            return ResponseEntity.badRequest().body(Map.of("message", "Verification Unsuccessful. Please try again."));
        }
        else{
            try {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(otpRequest.getEmail(), rawPassword);

                Authentication authentication = authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

            }
            catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(Map.of("message", "Verification Unsuccessful. Please Try Again."));
            }
            return ResponseEntity.ok(Map.of("redirectUrl", "/profile"));
        }
    }

    @PostMapping("/signup/save")
    public String submitSignupForm(@Valid @ModelAttribute("userObj") User registeredUser, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()) {
            return "signup-page";
        }

        if (userService.getUserByEmail(registeredUser.getEmail())) {
            bindingResult.rejectValue("username", "error.user", "Username already exists.");
            return "signup-page";
        }

        String rawPassword = registeredUser.getPassword();
        userService.saveUser(registeredUser);

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(registeredUser.getEmail(), rawPassword);

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

        }
        catch (Exception e) {
            System.out.println("❌ Authentication failed: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/login?error"; // redirect to login if auto-authentication fails
        }

        return "redirect:/";
    }

    //In the Modal, There is a button at top-right to close the modal. When it is pressed, This method will be called.
    @PostMapping("/remove-unverified-user")
    @ResponseBody
    public void removeUnverifiedUser(@RequestBody Map<String, String> response){
        String email = response.get("email");

        Optional<UnverifiedUser> unverifiedUser = unverifiedUserRepo.findByEmail(email);
        unverifiedUser.ifPresent(unverifiedUserRepo::delete);
    }

}
