package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
/*
    public CartResponse getCartItems(HttpServletRequest request) {
        Optional<String> token = jwtUtil.getJwtFromCookies(request);
        if (token.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        Long id = jwtUtil.extractUserId(token.get());
        if (!jwtUtil.validateToken(token.get(), id)) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        return new CartResponse();
        return new CartResponse(cartRepository.findAllByUserId(id));
    }
    public CartResponse changeCart(HttpServletRequest request, Long itemId) {
        Long userId = jwtUtil.getUserIdFromCookie(request);
        CartItem cartItem = new CartItem(1, userId, itemId);
        List<CartItem> cartItems = cartRepository.findAllByUserId(userId);
        if (cartItems.isEmpty()) {
            cartItems.add(cartItem);
            return new CartResponse(cartItems);
        }
        if (cartItems.contains(cartItem)) {
            cartItems.remove(cartItem);
        }
        cartItems.add(cartItem);
        return new CartResponse(cartItems);

    }*/
}
