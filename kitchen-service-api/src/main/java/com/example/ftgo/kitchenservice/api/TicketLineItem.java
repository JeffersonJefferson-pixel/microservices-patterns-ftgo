package com.example.ftgo.kitchenservice.api;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class TicketLineItem {
    private int quantity;
    private String menuItemId;
    private String name;
}
