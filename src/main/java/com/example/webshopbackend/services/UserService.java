package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.User.CreateUser;
import com.example.webshopbackend.dtos.User.EditUser;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import com.example.webshopbackend.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }
    public Optional<User> findUserById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            return optUser;
        }
        throw new HttpServerErrorException(HttpStatusCode.valueOf(404), "Not found");
    }
    public UserResponse createUser(CreateUser newUser) {
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAdmin(), user.getPassword());
    }
    public UserResponse updateUser(Long id, EditUser editUser) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(404), "Not found");
        }
        User user = optUser.get();
        if (editUser.getName() != null) {
            user.setName(editUser.getName());
        }
        if (editUser.getPassword() != null) {
            user.setPassword(editUser.getPassword());
        }
        if (editUser.getEmail() != null) {
            user.setEmail(editUser.getEmail());
        }

        user = userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAdmin(), user.getPassword());

    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
