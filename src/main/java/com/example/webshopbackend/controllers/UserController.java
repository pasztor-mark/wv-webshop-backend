package com.example.webshopbackend.controllers;

import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.EditUser;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.responses.UserResponse;
import com.example.webshopbackend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();
        return users.isEmpty() ? List.of() : users;
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.orElse(null);
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUser user) {
        UserResponse createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody EditUser user) {
        UserResponse userResponse = userService.updateUser(id, user);
        return ResponseEntity.ok(userResponse);
    }
    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
    @GetMapping("self")
    public ResponseEntity<UserResponse> getSelf(HttpServletRequest request) {
        UserResponse  userResponse = userService.getSelf(request);
        return ResponseEntity.ok(userResponse);
    }
}
