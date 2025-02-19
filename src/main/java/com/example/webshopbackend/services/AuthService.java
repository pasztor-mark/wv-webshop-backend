package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.Login;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void assignTokenToCookie(HttpServletResponse response, Long id) {
        String token = jwtUtil.generateToken(id);
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(3600 * 8);

        response.addCookie(jwtCookie);
    }

    public String register(CreateUser createUser, HttpServletResponse response) {
        if (userRepository.getUserByEmail(createUser.getEmail()).isPresent()) {
            return "User already exists";
        }

        User registeredUser = User.builder()
                .username(createUser.getName())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .email(createUser.getEmail())
                .build();
        userRepository.save(registeredUser);

        assignTokenToCookie(response, registeredUser.getId());
        return "User registered successfully";
    }
    public String login(Login loginData, HttpServletResponse response) {
        User matchingUser = userRepository.getUserByEmail(loginData.getEmail()).orElse(null);
        if (matchingUser == null ) {
            return "Check your credentials";
        }        if (!passwordEncoder.matches(loginData.getPassword(), matchingUser.getPassword())) {
            return "Check your credentials";
        }
        assignTokenToCookie(response, matchingUser.getId());
        return "User logged in successfully";
    }

}
