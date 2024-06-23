package com.example.microservices_patterns_ftgo.api;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelCreateTicket implements Command {
    private Long ticketId;
}
