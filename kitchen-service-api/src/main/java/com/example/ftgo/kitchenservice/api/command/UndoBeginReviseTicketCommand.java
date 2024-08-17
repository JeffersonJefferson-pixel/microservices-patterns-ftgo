package com.example.ftgo.kitchenservice.api.command;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UndoBeginReviseTicketCommand implements Command {
    private Long restaurantId;
    private Long orderId;
}
