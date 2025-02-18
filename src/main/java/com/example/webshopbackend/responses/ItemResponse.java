package com.example.webshopbackend.responses;

import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Builder

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Byte[] image;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.image = item.getImage();
    }
}
