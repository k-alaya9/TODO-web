package com.example.todoapp.config;


import com.example.todoapp.service.CustomOidcUserService;
import com.example.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Currency;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomOidcUserService oidcUserService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
                    auth.requestMatchers("/register.html").permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(auth->{auth.disable();})
                .oauth2Login(
                        oauth->
                                oauth.userInfoEndpoint(
                                        userinfo->userinfo.oidcUserService(oidcUserService)
                                )
                )
                .userDetailsService(userService)
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
