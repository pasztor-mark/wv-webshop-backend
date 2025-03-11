package com.example.webshopbackend.responses;

import com.example.webshopbackend.models.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private int itemCount;
    private List<ItemResponse> cart;

    public CartResponse(List<Item> cart) {
        this.itemCount = cart.size();
        this.cart = (cart.stream().map(ItemResponse::new)).toList();
    }


}
