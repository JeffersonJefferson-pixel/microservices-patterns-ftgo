package com.example.ftgo.kitchenservice.api;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketDetails {
    private List<TicketLineItem> lineItems;
}
