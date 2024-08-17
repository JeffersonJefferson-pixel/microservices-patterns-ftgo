package com.example.ftgo.orderservice.api.events;

import com.example.ftgo.common.Address;
import com.example.ftgo.orderservice.api.OrderDetails;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderCreatedEvent implements OrderDomainEvent {
    private OrderDetails orderDetails;
    private Address address;
    private String restaurantName;
}
