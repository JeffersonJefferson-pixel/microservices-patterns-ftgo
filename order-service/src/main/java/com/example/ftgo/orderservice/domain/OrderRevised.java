package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Money;
import com.example.ftgo.orderservice.api.events.OrderDomainEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderRevised implements OrderDomainEvent {
    private final OrderRevision orderRevision;
    private final Money currentOrderTotal;
    private final Money newOrderTotal;
}
