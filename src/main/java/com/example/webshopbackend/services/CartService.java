package com.example.webshopbackend.services;

import com.example.webshopbackend.dtos.CartItem.CartItem;
import com.example.webshopbackend.models.CartItems;
import com.example.webshopbackend.models.Item;
import com.example.webshopbackend.models.User;
import com.example.webshopbackend.repositories.CartRepository;
import com.example.webshopbackend.repositories.ItemRepository;
import com.example.webshopbackend.responses.CartResponse;
import com.example.webshopbackend.utils.JwtUtil;
import com.example.webshopbackend.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
    private final UserUtils userUtils;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public CartResponse getCartItems() {
        User user = userUtils.getCurrentUser();
        List<CartItem> cartItems = cartRepository.findByUserId(user.getId()).stream().map(c -> new CartItem(c)).collect(Collectors.toList());
        return new CartResponse(cartItems.stream().map(i -> itemService.findById(i.getItemId()).orElse(null)).collect(Collectors.toList()));
    }

    @Transactional
    public CartResponse changeCart(Long itemId) {
        User user = userUtils.getCurrentUser();
        CartItem cartItem = new CartItem(1, user.getId(), itemId);
        Optional<Item> item = itemService.findById(itemId);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        List<CartItems> dbItems = cartRepository.findByUserId(user.getId());
        List<CartItem> cartItems = dbItems.stream().map(c -> new CartItem(c)).collect(Collectors.toList());
        if (cartItems.stream().noneMatch(o -> o.getItemId().equals(cartItem.getItemId()))) {
            cartItems.add(cartItem);
            cartRepository.save(new CartItems(user, item.get(), 1));
            return new CartResponse(cartItems.stream().map(i -> itemService.findById(i.getItemId()).orElse(null)).collect(Collectors.toList()));
        } else {
            cartRepository.deleteByUser_IdAndItem_Id(user.getId(), itemId);
            return new CartResponse(cartRepository.findByUserId(user.getId()).stream().map(i -> i.getItem()).collect(Collectors.toList()));
        }

    }
}
