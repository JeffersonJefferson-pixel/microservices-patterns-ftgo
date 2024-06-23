package com.example.microservices_patterns_ftgo.api;

import com.example.microservices_patterns_ftgo.common.Money;
import lombok.Getter;

@Getter
public class OrderDetails {
    private Money orderTotal;
    private Long restaurantId;
    private Long consumerId;
}
