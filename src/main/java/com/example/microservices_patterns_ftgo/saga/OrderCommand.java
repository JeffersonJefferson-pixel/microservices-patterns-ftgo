package com.example.microservices_patterns_ftgo.saga;

import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public abstract class OrderCommand implements Command {
    private Long orderId;

    protected OrderCommand(Long orderId) {
        this.orderId = orderId;
    }
}
