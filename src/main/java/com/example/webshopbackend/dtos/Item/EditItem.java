package com.example.webshopbackend.dtos.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditItem {
    private Byte[] image;
    private String name;
    private String description;
}
