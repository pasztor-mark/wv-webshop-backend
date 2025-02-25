package com.example.webshopbackend.repositories;

import com.example.webshopbackend.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findAllByUser_Id(Long userId);
}
