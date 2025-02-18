package com.example.webshopbackend.dtos.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItem {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Byte[] image;
}
