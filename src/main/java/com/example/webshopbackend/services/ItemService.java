package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.Item.CreateItem;
import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.ItemRepository;
import com.example.webshopbackend.responses.ItemResponse;
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

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
    public ItemResponse updateItem(Long id, Item newItem) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(404));
        }
        Item update = item.get();
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
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
