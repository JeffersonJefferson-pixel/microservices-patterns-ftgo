package com.example.ftgo.kitchenservice.api.event;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TicketAcceptedEvent implements TicketDomainEvent {
    private LocalDateTime readyBy;
}
