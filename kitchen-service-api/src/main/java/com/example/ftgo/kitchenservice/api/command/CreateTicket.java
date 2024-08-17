package com.example.ftgo.kitchenservice.api.command;

import com.example.ftgo.kitchenservice.api.TicketDetails;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTicket implements Command {
    private Long orderId;
    private TicketDetails ticketDetails;
    private Long restaurantId;
}
