package com.example.ftgo.orderservice.messaging;

import com.example.ftgo.orderservice.domain.OrderService;
import com.example.ftgo.orderservice.saga.ApproveOrderCommand;
import com.example.ftgo.orderservice.saga.RejectOrderCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
@RequiredArgsConstructor
public class OrderCommandHandlers {
    private final OrderService orderService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder.fromChannel("orderService")
                .onMessage(ApproveOrderCommand.class, this::approveOrder)
                .onMessage(RejectOrderCommand.class, this::rejectOrder)
                .build();
    }

    public Message approveOrder(CommandMessage<ApproveOrderCommand> cm) {
        Long orderId  = cm.getCommand().getOrderId();
        orderService.approveOrder(orderId);
        return withSuccess();
    }

    public Message rejectOrder(CommandMessage<RejectOrderCommand> cm) {
        Long orderId = cm.getCommand().getOrderId();
        orderService.rejectOrder(orderId);
        return withSuccess();
    }
}
