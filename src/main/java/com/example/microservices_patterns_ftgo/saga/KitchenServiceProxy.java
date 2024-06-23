package com.example.microservices_patterns_ftgo.saga;

import com.example.microservices_patterns_ftgo.api.*;
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
