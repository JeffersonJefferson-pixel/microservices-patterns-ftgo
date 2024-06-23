package com.example.microservices_patterns_ftgo.api;

import com.example.microservices_patterns_ftgo.common.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateOrderByConsumer implements Command {
    private Long consumerId;
    private Long orderId;
    private Money orderTotal;
}
