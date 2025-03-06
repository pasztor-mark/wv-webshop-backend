package com.example.webshopbackend.controllers;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.Item.CreateItem;
import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.ItemRepository;
import com.example.webshopbackend.responses.ItemResponse;
import com.example.webshopbackend.services.ItemService;
import com.example.webshopbackend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemService itemService, JwtUtil jwtUtil, UserService userService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/feed")
    public Page<ItemResponse> getItems(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        Page<ItemResponse> itemResponses = items.map(ItemResponse::new);
        return itemResponses;
    }

    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            return item.get();
        }
        throw new HttpServerErrorException(HttpStatusCode.valueOf(404));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody CreateItem newItem, HttpServletRequest request) {
        User user = userService.getSelf(request);
        ItemResponse createdItem = itemService.createItem(newItem, user);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody Item newItem, HttpServletRequest request) {
        ItemResponse updatedItem = itemService.updateItem(id, newItem, request);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id, HttpServletRequest request) {
        itemService.deleteItem(id, request);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }


}
