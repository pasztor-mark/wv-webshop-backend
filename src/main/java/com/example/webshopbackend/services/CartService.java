package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.CartItem.CartItem;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.CartRepository;
import com.example.webshopbackend.responses.CartResponse;
import com.example.webshopbackend.utils.JwtUtil;
import com.example.webshopbackend.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
    private final UserUtils userUtils;

    public CartResponse getCartItems() {
        User user = userUtils.getCurrentUser();
        List<CartItem> cartItems = cartRepository.findByUserId(user.getId()).stream().map(c -> new CartItem(c)).collect(Collectors.toList());
        return new CartResponse(cartItems);
    }
    public CartResponse changeCart(HttpServletRequest request, Long itemId) {
        Long userId = jwtUtil.getSelf(request).getId();
        CartItem cartItem = new CartItem(1, userId, itemId);
        List<CartItem> cartItems = cartRepository.findByUserId(userId).stream().map(c -> new CartItem(c)).collect(Collectors.toList());
        if (cartItems.isEmpty()) {
            cartItems.add(cartItem);
            return new CartResponse(cartItems);
        }
        cartItems.remove(cartItem);
        cartItems.add(cartItem);
        return new CartResponse(cartItems);

    }
}
