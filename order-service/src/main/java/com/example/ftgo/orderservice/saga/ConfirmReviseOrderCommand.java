package com.example.ftgo.orderservice.saga;

import com.example.ftgo.orderservice.domain.OrderRevision;
import lombok.Getter;

@Getter
public class ConfirmReviseOrderCommand extends OrderCommand {
    private OrderRevision revision;

    public ConfirmReviseOrderCommand(Long orderId, OrderRevision revision) {
        super(orderId);
        this.revision = revision;
    }
}
