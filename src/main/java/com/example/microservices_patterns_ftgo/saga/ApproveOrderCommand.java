package com.example.microservices_patterns_ftgo.saga;

public class ApproveOrderCommand extends OrderCommand {
    public ApproveOrderCommand(Long orderId) {
        super(orderId);
    }
}
