package com.example.ftgo.orderservice.saga;

import com.example.ftgo.consumerservice.api.ConsumerServiceChannels;
import com.example.ftgo.consumerservice.api.ValidateOrderByConsumer;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerServiceProxy {
    public final CommandEndpoint<ValidateOrderByConsumer> validateOrder = CommandEndpointBuilder.forCommand(ValidateOrderByConsumer.class)
            .withChannel(ConsumerServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
