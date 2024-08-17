package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineItemQuantityChange {
    private final Money currentOrderTotal;
    private final Money newOrderTotal;
    private final Money delta;
}
