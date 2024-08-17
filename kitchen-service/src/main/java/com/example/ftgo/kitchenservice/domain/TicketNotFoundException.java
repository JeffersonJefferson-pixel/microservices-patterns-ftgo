package com.example.ftgo.kitchenservice.domain;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long orderId) {
        super("Ticket not found: " + orderId);
    }
}
