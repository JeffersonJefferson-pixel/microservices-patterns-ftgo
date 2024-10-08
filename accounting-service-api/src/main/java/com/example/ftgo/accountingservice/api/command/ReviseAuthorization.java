package com.example.ftgo.accountingservice.api.command;

import com.example.ftgo.common.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviseAuthorization implements Command {
    private Long consumerId;
    private Long orderId;
    private Money orderTotal;
}
