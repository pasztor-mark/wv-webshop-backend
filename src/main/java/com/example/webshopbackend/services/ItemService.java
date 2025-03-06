package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.Item.CreateItem;
import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.ItemRepository;
import com.example.webshopbackend.responses.ItemResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public ItemService(ItemRepository itemRepository, JwtUtil jwtUtil, UserService userService) {
        this.itemRepository = itemRepository;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    public Optional<Item> findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item;
        }
        throw new HttpServerErrorException(HttpStatusCode.valueOf(404));
    }

    public ItemResponse createItem(CreateItem newItem, User user) {
        Byte[] imageBytes = newItem.getImage();
        byte[] imageByteArray = new byte[imageBytes.length];
        for (int i = 0; i < imageBytes.length; i++) {
            imageByteArray[i] = imageBytes[i];
        }

        String encoded = Base64.getEncoder().encodeToString(imageByteArray);
        Item item = new Item();
        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());
        item.setDescription(newItem.getDescription());
        item.setImage(encoded);
        item.setAuthor(user);

        itemRepository.save(item);
        return new ItemResponse(item);
    }
    public ItemResponse updateItem(Long id, Item newItem, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromCookie(request);
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(404));
        }
        Item update = item.get();
        if (!update.getAuthor().getId().equals(userId)) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(403));
        }
        if (newItem.getName() != null) {
            update.setName(newItem.getName());
        }
        if (newItem.getPrice() != null) {
            update.setPrice(newItem.getPrice());
        }
        if (newItem.getDescription() != null) {
            update.setDescription(newItem.getDescription());
        }
        if (newItem.getImage() != null) {
            update.setImage(newItem.getImage());
        }

        itemRepository.save(update);
        return new ItemResponse(update);
    }
    public void deleteItem(Long id, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromCookie(request);
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(404));
        }
        if (!item.get().getAuthor().getId().equals(userId)) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(403));
        }
        itemRepository.deleteById(id);
    }

}
