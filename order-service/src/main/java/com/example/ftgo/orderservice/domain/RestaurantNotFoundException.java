package com.example.ftgo.orderservice.domain;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long restaurantId) {
        super("Restaurant not found with id " + restaurantId);
    }
}
