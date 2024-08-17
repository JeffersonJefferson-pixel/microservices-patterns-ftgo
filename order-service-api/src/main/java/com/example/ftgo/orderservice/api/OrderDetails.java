package com.example.ftgo.orderservice.api;

import com.example.ftgo.common.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetails {
    private List<OrderLineItem> lineItems;
    private Money orderTotal;
    private Long restaurantId;
    private Long consumerId;
}
