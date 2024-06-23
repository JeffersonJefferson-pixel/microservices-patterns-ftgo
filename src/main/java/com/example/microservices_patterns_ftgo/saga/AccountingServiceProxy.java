package com.example.microservices_patterns_ftgo.saga;

import com.example.microservices_patterns_ftgo.api.AccountingServiceChannels;
import com.example.microservices_patterns_ftgo.api.AuthorizeCommand;
import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountingServiceProxy {
    public final CommandEndpoint<AuthorizeCommand> authorize = CommandEndpointBuilder.forCommand(AuthorizeCommand.class)
            .withChannel(AccountingServiceChannels.COMMAND_CHANNEL)
            .withReply(Success.class)
            .build();
}
