package com.example.webshopbackend.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {
    private ItemResponse item;

    public CartItemResponse(ItemResponse item) {
        this.item = item;
    }
}
