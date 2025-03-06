package com.example.webshopbackend.configs;

import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.ItemRepository;
import com.example.webshopbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        User adminUser = User.builder()
                .username("admin")
                .password("Administrator123!")
                .email("admin@admin.com")
                .admin(true)
                .build();
        userRepository.save(adminUser);

        Item testItem = Item.builder()
                .name("Test Item")
                .description("This is a test item")
                .price(2500)
                .image("")
                .author(adminUser)
                .build();
        itemRepository.save(testItem);
    }
}
