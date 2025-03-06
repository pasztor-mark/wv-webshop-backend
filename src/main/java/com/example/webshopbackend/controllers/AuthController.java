package com.example.webshopbackend.controllers;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.Login;
import com.example.webshopbackend.responses.AuthResponse;
import com.example.webshopbackend.services.AuthService;
import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<AuthResponse> register(@RequestBody CreateUser createUser, HttpServletResponse response) {
        AuthResponse res = authService.register(createUser, response);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login login, HttpServletResponse response) {
        AuthResponse res = authService.login(login, response);
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
    @DeleteMapping("/invalidate")
    public void invalidate(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        response.addCookie(cookie);
    }


}
