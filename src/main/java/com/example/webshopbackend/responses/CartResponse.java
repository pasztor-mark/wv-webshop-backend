package com.example.webshopbackend.responses;

import com.example.webshopbackend.dtos.CartItem.CartItem;
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
    private List<CartItem> cart;

    public CartResponse(List<CartItem> cartItems) {
        this.itemCount = cartItems.size();
        this.cart = cartItems;
    }

}
