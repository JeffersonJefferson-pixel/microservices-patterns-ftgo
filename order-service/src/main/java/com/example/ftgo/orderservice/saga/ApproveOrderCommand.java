package com.example.ftgo.orderservice.saga;

public class ApproveOrderCommand extends OrderCommand {
    public ApproveOrderCommand(Long orderId) {
        super(orderId);
    }
}
