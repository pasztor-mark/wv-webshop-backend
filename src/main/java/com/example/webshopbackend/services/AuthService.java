package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.Login;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import com.example.webshopbackend.responses.UserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

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

    public UserResponse register(CreateUser createUser, HttpServletResponse response) {
        if (userRepository.getUserByEmail(createUser.getEmail()).isPresent()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(409));
        }

        User registeredUser = User.builder()
                .username(createUser.getName())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .email(createUser.getEmail())
                .build();
        userRepository.save(registeredUser);

        assignTokenToCookie(response, registeredUser.getId());
        return UserResponse.builder()
                .name(registeredUser.getUsername())
                .email(registeredUser.getEmail())
                .id(registeredUser.getId())
                .build();
    }

    public UserResponse login(Login loginData, HttpServletResponse response) {
        User matchingUser = userRepository.getUserByEmail(loginData.getEmail()).orElse(null);
        if (matchingUser == null) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        if (!passwordEncoder.matches(loginData.getPassword(), matchingUser.getPassword())) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        assignTokenToCookie(response, matchingUser.getId());
        return UserResponse.builder().email(matchingUser.getEmail()).admin(matchingUser.getAdmin()).id(matchingUser.getId()).name(matchingUser.getUsername()).build();
    }

}
