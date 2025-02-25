package com.example.webshopbackend.responses;

import com.example.webshopbackend.dtos.CartItem.CartItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {
    private List<CartItem> cart;

    public CartResponse(List<CartItem> cart) {
        this.cart = cart;
    }
}
