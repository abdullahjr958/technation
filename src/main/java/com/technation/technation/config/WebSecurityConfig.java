package com.technation.technation.config;

import com.technation.technation.component.CustomLoginSuccessHandler;
import com.technation.technation.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomLoginSuccessHandler successHandler;
    private final CustomUserDetailsService userDetailsService;

    WebSecurityConfig(CustomLoginSuccessHandler successHandler, CustomUserDetailsService userDetailsService){
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/normal/product-details/**",
                                "/normal/category/**",
                                "/cart-item/delete/**",
                                "/add-to-cart/**",
                                "/category",
                                "/signup/save",
                                "/login",
                                "/signup",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/profile", "/checkout").authenticated()
                        .anyRequest().permitAll())
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                            "default-src 'self';" +
                                            "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.tailwindcss.com https://cdn.jsdelivr.net https://fonts.googleapis.com https://kit.fontawesome.com;" +
                                            "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdn.jsdelivr.net https://cdnjs.cloudflare.com;" +
                                            "font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com;" +
                                            "img-src 'self' https: data:;" +
                                            "connect-src 'self' https://ka-f.fontawesome.com;"
                                )
                        )
                )
                .formLogin((form) -> form.
                        loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .sessionManagement(session -> session
                        .sessionFixation(sf -> sf.none()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }
}
