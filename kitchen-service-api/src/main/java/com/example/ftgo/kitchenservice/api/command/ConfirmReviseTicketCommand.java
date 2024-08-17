package com.example.ftgo.kitchenservice.api.command;

import com.example.ftgo.orderservice.api.RevisedOrderLineItem;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ConfirmReviseTicketCommand implements Command {
    private Long restaurantId;
    private Long orderId;
    private List<RevisedOrderLineItem> revisedOrderLineItems;
}
