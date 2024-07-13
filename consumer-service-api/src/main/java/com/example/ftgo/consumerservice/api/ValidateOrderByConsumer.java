package com.example.ftgo.consumerservice.api;

import com.example.ftgo.common.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateOrderByConsumer implements Command {
    private Long consumerId;
    private Long orderId;
    private Money orderTotal;
}
