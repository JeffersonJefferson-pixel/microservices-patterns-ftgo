package com.example.microservices_patterns_ftgo.domain;

import io.eventuate.tram.events.publisher.ResultWithEvents;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    public static ResultWithEvents<Order> createOrder() {
        return null;
    };
}
