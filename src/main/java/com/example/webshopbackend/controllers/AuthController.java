package com.example.webshopbackend.controllers;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.Login;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import com.example.webshopbackend.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUser createUser, HttpServletResponse response) {
        String res = authService.register(createUser, response);

        if (res.equals("User already exists")) {
            return ResponseEntity.badRequest().body(res);
        }
        return ResponseEntity.ok().body(null);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login, HttpServletResponse response) {
        String res = authService.login(login, response);
        if (res.equals("Check your credentials")) {
            return ResponseEntity.badRequest().body(res);
        }
        return ResponseEntity.ok().body(res);
    }


}
