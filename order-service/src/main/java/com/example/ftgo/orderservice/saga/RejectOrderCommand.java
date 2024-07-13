package com.example.ftgo.orderservice.saga;

public class RejectOrderCommand extends OrderCommand {
    public RejectOrderCommand(Long orderId) {
        super(orderId);
    }
}
