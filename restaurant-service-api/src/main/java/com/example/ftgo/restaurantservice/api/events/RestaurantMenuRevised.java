package com.example.ftgo.restaurantservice.api.events;

import com.example.ftgo.restaurantservice.api.RestaurantMenu;
import lombok.Getter;

@Getter
public class RestaurantMenuRevised implements RestaurantDomainEvent {
    private RestaurantMenu menu;
}
