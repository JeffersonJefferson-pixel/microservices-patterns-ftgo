package com.example.ftgo.kitchenservice.api;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelCreateTicket implements Command {
    private Long ticketId;
}
