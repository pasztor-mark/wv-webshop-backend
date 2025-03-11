package com.example.webshopbackend.utils;

import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() throws ResponseStatusException {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userRepository.findById(user.getId()).orElseThrow();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
