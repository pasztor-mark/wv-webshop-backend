package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.repositories.UserRepository;
import com.example.webshopbackend.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserResponse register(CreateUser createUser) {
        if (userService.isUserAlreadyRegistered(createUser.getEmail())) {
            CreateUser registeredUser = new CreateUser(createUser.getName(), createUser.getEmail(), encoder.encode(createUser.getPassword()));
            return userService.createUser(registeredUser);
        }
        throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
    }


}
