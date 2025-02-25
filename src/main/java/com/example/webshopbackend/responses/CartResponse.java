package com.example.webshopbackend.responses;

import com.example.webshopbackend.models.CartItems;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponse {
    private List<CartItems> cart;

    public CartResponse(List<CartItems> cart) {
        this.cart = cart;
    }
}
