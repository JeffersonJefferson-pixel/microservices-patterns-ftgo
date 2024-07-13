package com.example.ftgo.orderservice.saga;

import io.eventuate.tram.commands.common.Command;
import lombok.Data;

@Data
public abstract class OrderCommand implements Command {
    private Long orderId;

    protected OrderCommand(Long orderId) {
        this.orderId = orderId;
    }
}
