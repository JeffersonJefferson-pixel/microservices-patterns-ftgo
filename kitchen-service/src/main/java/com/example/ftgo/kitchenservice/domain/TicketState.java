package com.example.ftgo.kitchenservice.domain;

public enum TicketState {
    CREATE_PENDING,
    AWAITING_ACCEPTANCE,
    ACCEPTED, PREPARING,
    READY_FOR_PICKUP,
    PICKED_UP,
    CANCELED_PENDING,
    CANCELED,
    REVISION_PENDING,
}
