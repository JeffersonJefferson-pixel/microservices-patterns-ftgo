package com.example.ftgo.orderservice.saga;

import com.example.ftgo.accountingservice.api.AccountingServiceChannels;
import com.example.ftgo.accountingservice.api.command.AuthorizeCommand;
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
