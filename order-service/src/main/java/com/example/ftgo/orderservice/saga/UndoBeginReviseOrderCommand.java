package com.example.ftgo.orderservice.saga;

import lombok.AllArgsConstructor;

public class UndoBeginReviseOrderCommand extends OrderCommand {

    public UndoBeginReviseOrderCommand(Long orderId) {
        super(orderId);
    }
}
