package com.example.webshopbackend.repositories;

import com.example.webshopbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
