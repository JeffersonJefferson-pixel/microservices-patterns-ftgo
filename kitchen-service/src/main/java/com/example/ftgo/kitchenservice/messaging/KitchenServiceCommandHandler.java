package com.example.ftgo.kitchenservice.messaging;

import com.example.ftgo.kitchenservice.api.CreateTicketReply;
import com.example.ftgo.kitchenservice.api.TicketDetails;
import com.example.ftgo.kitchenservice.api.command.CancelCreateTicket;
import com.example.ftgo.kitchenservice.api.command.ConfirmCreateTicket;
import com.example.ftgo.kitchenservice.api.command.CreateTicket;
import com.example.ftgo.kitchenservice.domain.KitchenService;
import com.example.ftgo.kitchenservice.domain.RestaurantDetailsVerificationException;
import com.example.ftgo.kitchenservice.domain.Ticket;
import com.example.ftgo.orderservice.api.OrderServiceChannels;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
@RequiredArgsConstructor
public class KitchenServiceCommandHandler {
    private KitchenService kitchenService;

    public CommandHandlers commandHandlers() {
        return CommandHandlersBuilder.fromChannel(OrderServiceChannels.COMMAND_CHANNEL)
                .onMessage(CreateTicket.class, this::createTicket)
                .onMessage(ConfirmCreateTicket.class, this::confirmCreateTicket)
                .onMessage(CancelCreateTicket.class, this::cancelCreateTicket)
                .build();
    }

    private Message createTicket(CommandMessage<CreateTicket> commandMessage) {
        CreateTicket command = commandMessage.getCommand();
        Long restaurantId = command.getRestaurantId();
        Long ticketId = command.getOrderId();
        TicketDetails ticketDetails = command.getTicketDetails();

        try {
            Ticket ticket = kitchenService.createTicket(restaurantId, ticketId, ticketDetails);

            CreateTicketReply reply = new CreateTicketReply(ticket.getId());
            return withSuccess(reply);
        } catch (RestaurantDetailsVerificationException e) {
            return withFailure();
        }
    }

    private Message confirmCreateTicket(CommandMessage<ConfirmCreateTicket> commandMessage) {
        Long ticketId = commandMessage.getCommand().getTicketId();
        kitchenService.confirmCreateTicket(ticketId);
        return withSuccess();
    }

    private Message cancelCreateTicket(CommandMessage<CancelCreateTicket> commandMessage) {
        Long ticketId = commandMessage.getCommand().getTicketId();
        kitchenService.cancelCreateTicket(ticketId);
        return withSuccess();
    }
}
