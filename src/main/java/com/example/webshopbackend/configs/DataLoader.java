package com.example.webshopbackend.configs;

import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        User adminUser = User.builder()
                .username("admin")
                .password("admin")
                .email("admin@admin.com")
                .admin(true)
                .build();
        userRepository.save(adminUser);
    }
}
