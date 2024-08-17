package com.example.ftgo.orderservice.saga;

import com.example.ftgo.orderservice.domain.OrderRevision;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeginReviseOrderCommand extends OrderCommand {
    private OrderRevision revision;
    public BeginReviseOrderCommand(Long orderId, OrderRevision revision) {
        super(orderId);
        this.revision = revision;
    }
}
