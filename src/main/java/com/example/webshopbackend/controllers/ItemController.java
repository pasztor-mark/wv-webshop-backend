package com.example.webshopbackend.controllers;

import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.responses.ItemResponse;
import com.example.webshopbackend.services.ItemService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.findAll();
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
    public ResponseEntity<ItemResponse> createItem(@RequestBody Item newItem) {
       ItemResponse createdItem = itemService.createItem(newItem);
       return ResponseEntity.ok(createdItem);
    }
    @PutMapping("item/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody Item newItem) {
        ItemResponse updatedItem = itemService.updateItem(id, newItem);
        return ResponseEntity.ok(updatedItem);
    }
    @DeleteMapping("item/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

}
