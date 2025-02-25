package com.example.webshopbackend.controllers;

import com.example.webshopbackend.responses.CartResponse;
import com.example.webshopbackend.services.CartService;
import com.example.webshopbackend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("own")
    public CartResponse getOwnCart(HttpServletRequest request) {
        return cartService.getCartItems(request);
    }
    @PostMapping("change")
    public CartResponse changeCart(HttpServletRequest request)  {
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500));
    }
}
