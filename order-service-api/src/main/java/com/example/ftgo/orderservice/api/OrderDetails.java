package com.example.ftgo.orderservice.api;

import com.example.ftgo.common.Money;
import lombok.Getter;

@Getter
public class OrderDetails {
    private Money orderTotal;
    private Long restaurantId;
    private Long consumerId;
}
