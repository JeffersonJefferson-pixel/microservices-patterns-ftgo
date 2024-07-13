package com.example.ftgo.orderservice.saga;

import com.example.ftgo.orderservice.api.OrderServiceChannels;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceProxy {
    public final CommandEndpoint<RejectOrderCommand> reject = CommandEndpointBuilder.forCommand(RejectOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();

    public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder.forCommand(ApproveOrderCommand.class)
            .withChannel(OrderServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
