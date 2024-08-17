package com.example.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmCreateTicket implements Command {
    private Long ticketId;
}
