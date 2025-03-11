package com.example.webshopbackend.controllers;

import com.example.webshopbackend.responses.CartResponse;
import com.example.webshopbackend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart/")
public class CartController {
    private final CartService cartService;

    @GetMapping("own")
    public CartResponse getOwnCart() {
        return cartService.getCartItems();
    }

    @PostMapping("change/{itemId}")
    public CartResponse changeCart(@PathVariable Long itemId) {
        return cartService.changeCart(itemId);
    }
}
