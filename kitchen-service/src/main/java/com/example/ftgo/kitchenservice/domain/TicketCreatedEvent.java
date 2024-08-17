package com.example.ftgo.kitchenservice.domain;

import com.example.ftgo.kitchenservice.api.TicketDetails;
import com.example.ftgo.kitchenservice.api.event.TicketDomainEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TicketCreatedEvent implements TicketDomainEvent {
    private Long id;
    private TicketDetails details;
}
