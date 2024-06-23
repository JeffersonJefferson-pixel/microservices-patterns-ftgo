package com.example.microservices_patterns_ftgo.api;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTicket implements Command {
    private Long orderId;
    private TicketDetails ticketDetails;
    private Long restaurantId;
}
