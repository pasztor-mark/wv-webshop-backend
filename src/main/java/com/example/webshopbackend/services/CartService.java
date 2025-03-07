package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.dtos.CartItem.CartItem;
import com.example.webshopbackend.repositories.CartRepository;
import com.example.webshopbackend.responses.CartResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;

    public CartResponse getCartItems(HttpServletRequest request) {
        Long userId = jwtUtil.getSelf(request).getId();
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        return new CartResponse(cartItems);
    }
    public CartResponse changeCart(HttpServletRequest request, Long itemId) {
        Long userId = jwtUtil.getSelf(request).getId();
        CartItem cartItem = new CartItem(1, userId, itemId);
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            cartItems.add(cartItem);
            return new CartResponse(cartItems);
        }
        cartItems.remove(cartItem);
        cartItems.add(cartItem);
        return new CartResponse(cartItems);

    }
}
