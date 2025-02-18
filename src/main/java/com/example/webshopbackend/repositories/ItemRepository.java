package com.example.webshopbackend.repositories;

import com.example.webshopbackend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
