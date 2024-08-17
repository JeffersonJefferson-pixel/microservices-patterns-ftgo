package com.example.ftgo.orderservice.saga;

import com.example.ftgo.kitchenservice.api.*;
import com.example.ftgo.kitchenservice.api.command.CancelCreateTicket;
import com.example.ftgo.kitchenservice.api.command.ConfirmCreateTicket;
import com.example.ftgo.kitchenservice.api.command.CreateTicket;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class KitchenServiceProxy {
    public final CommandEndpoint<CreateTicket> create = CommandEndpointBuilder.forCommand(CreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANEL)
            .withReply(CreateTicketReply.class)
            .build();

    public final CommandEndpoint<ConfirmCreateTicket> confirmCreate = CommandEndpointBuilder.forCommand(ConfirmCreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<CancelCreateTicket> cancel = CommandEndpointBuilder.forCommand(CancelCreateTicket.class)
            .withChannel(KitchenServiceChannels.COMMAND_CHANEL)
            .withReply(Success.class)
            .build();
}
