package com.example.webshopbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shop_cart_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItems {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantity", columnDefinition = "int default 1")
    @Builder.Default()
    private int quantity = 1;
}
