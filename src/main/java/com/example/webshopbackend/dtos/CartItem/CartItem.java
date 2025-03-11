package com.example.webshopbackend.dtos.CartItem;

import com.example.webshopbackend.models.CartItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {
    private Integer quantity;
    private Long userId;
    private Long itemId;

    public CartItem(CartItems cartItems) {
        this.quantity = cartItems.getQuantity();
        this.itemId = cartItems.getItem().getId();
        this.userId = cartItems.getUser().getId();
    }
}
