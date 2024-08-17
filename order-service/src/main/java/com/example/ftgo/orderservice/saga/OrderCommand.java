package com.example.ftgo.orderservice.saga;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class OrderCommand implements Command {
    private Long orderId;

    protected OrderCommand(Long orderId) {
        this.orderId = orderId;
    }
}
