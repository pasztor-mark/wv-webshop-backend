package com.example.webshopbackend.responses;

import com.example.webshopbackend.models.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String image;
    private UserResponse author;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.image = item.getImage();
        this.author = new UserResponse(item.getAuthor());
    }
}
