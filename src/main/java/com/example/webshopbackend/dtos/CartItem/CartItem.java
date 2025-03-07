package com.example.webshopbackend.dtos.CartItem;

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
}
