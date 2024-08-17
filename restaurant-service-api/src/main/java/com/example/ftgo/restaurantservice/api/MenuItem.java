package com.example.ftgo.restaurantservice.api;

import com.example.ftgo.common.Money;
import lombok.Data;

@Data
public class MenuItem {
    private String id;
    private String name;
    private Money price;
}
