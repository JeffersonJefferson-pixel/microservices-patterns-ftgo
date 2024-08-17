package com.example.ftgo.orderservice.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@Entity
public class Restaurant {
    @Id
    private Long id;

    @ElementCollection
    @CollectionTable
    private List<MenuItem> menuItems;
    private String name;

    public Optional<MenuItem> findMenuItem(String menuItemId) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(menuItemId))
                .findFirst();
    }
}
