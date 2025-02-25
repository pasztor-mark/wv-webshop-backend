package com.example.webshopbackend.services;

import com.example.webshopbackend.configs.JwtUtil;
import com.example.webshopbackend.repositories.CartRepository;
import com.example.webshopbackend.responses.CartResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;

    public CartResponse getCartItems(HttpServletRequest request) {
        Optional<String> token = jwtUtil.getJwtFromCookies(request);
        if (token.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        Long id = jwtUtil.extractUserId(token.get());
        if (!jwtUtil.validateToken(token.get(), id)) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(401));
        }
        return new CartResponse(cartRepository.findAllByUser_Id((id)));
    }
}
