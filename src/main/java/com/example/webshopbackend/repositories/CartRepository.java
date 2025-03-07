package com.example.webshopbackend.repositories;

import com.example.webshopbackend.dtos.CartItem.CartItem;
import com.example.webshopbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);

    Long user(User user);
}
