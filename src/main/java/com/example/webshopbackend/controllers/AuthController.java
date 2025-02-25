package com.example.webshopbackend.controllers;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.Login;
import com.example.webshopbackend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

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
    @GetMapping("/validity")
    public ResponseEntity<Boolean> validity(HttpServletRequest request) {
        Optional<String> token = jwtUtil.getJwtFromCookies(request);
        if (token.isPresent()) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
