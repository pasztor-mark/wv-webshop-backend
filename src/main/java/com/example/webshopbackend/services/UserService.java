package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.EditUser;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import com.example.webshopbackend.responses.UserResponse;
import com.example.webshopbackend.utils.JwtUtil;
import com.example.webshopbackend.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserUtils userUtils;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, UserUtils userUtils) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userUtils = userUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(401), "User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();

    }

    public Optional<User> findUserById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            return optUser;
        }
        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Not found");
    }

    public UserResponse createUser(CreateUser newUser) {
        User user = new User();
        user.setUsername(newUser.getName());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());

        user = userRepository.save(user);

        return new UserResponse(user);
    }

    public UserResponse updateUser(Long id, EditUser editUser) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(404), "Not found");
        }
        User user = optUser.get();
        if (editUser.getName() != null) {
            user.setUsername(editUser.getName());
        }
        if (editUser.getPassword() != null) {
            user.setPassword(editUser.getPassword());
        }
        if (editUser.getEmail() != null) {
            user.setEmail(editUser.getEmail());
        }

        user = userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAdmin());

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getSelf()   {
        return userUtils.getCurrentUser();
    }
}
