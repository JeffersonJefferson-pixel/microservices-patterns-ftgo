package com.example.microservices_patterns_ftgo.saga;

public class RejectOrderCommand extends OrderCommand {
    public RejectOrderCommand(Long orderId) {
        super(orderId);
    }
}
