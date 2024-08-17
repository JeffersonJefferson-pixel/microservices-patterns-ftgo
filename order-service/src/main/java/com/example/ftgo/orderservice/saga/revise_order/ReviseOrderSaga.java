package com.example.ftgo.orderservice.saga.revise_order;

import com.example.ftgo.accountingservice.api.AccountingServiceChannels;
import com.example.ftgo.accountingservice.api.command.ReviseAuthorization;
import com.example.ftgo.kitchenservice.api.KitchenServiceChannels;
import com.example.ftgo.kitchenservice.api.command.BeginReviseTicketCommand;
import com.example.ftgo.kitchenservice.api.command.ConfirmReviseTicketCommand;
import com.example.ftgo.kitchenservice.api.command.UndoBeginReviseTicketCommand;
import com.example.ftgo.orderservice.api.OrderServiceChannels;
import com.example.ftgo.orderservice.saga.BeginReviseOrderCommand;
import com.example.ftgo.orderservice.saga.BeginReviseOrderReply;
import com.example.ftgo.orderservice.saga.ConfirmReviseOrderCommand;
import com.example.ftgo.orderservice.saga.UndoBeginReviseOrderCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Component
@Getter
public class ReviseOrderSaga implements SimpleSaga<ReviseOrderSagaData> {
    private final SagaDefinition<ReviseOrderSagaData> sagaDefinition;

    public ReviseOrderSaga() {
        sagaDefinition = step()
                .invokeParticipant(this::beginReviseOrder)
                .onReply(BeginReviseOrderReply.class, this::handleBeginReviseOrderReply)
                .withCompensation(this::undoBeginReviseOrder)
                .step()
                .invokeParticipant(this::beginReviseTicket)
                .withCompensation(this::undoBeginReviseTicket)
                .step()
                .invokeParticipant(this::reviseAuthorization)
                .step()
                .invokeParticipant(this::confirmTicketRevision)
                .step()
                .invokeParticipant(this::confirmOrderRevision)
                .build();
    }

    private CommandWithDestination beginReviseOrder(ReviseOrderSagaData data) {
        return send(new BeginReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
                .to(OrderServiceChannels.COMMAND_CHANNEL)
                .build();
    }

    private void handleBeginReviseOrderReply(ReviseOrderSagaData data, BeginReviseOrderReply reply) {
        data.setRevisedOrderTotal(reply.getRevisedOrderTotal());
    }

    private CommandWithDestination undoBeginReviseOrder(ReviseOrderSagaData data) {
        return send(new UndoBeginReviseOrderCommand(data.getOrderId()))
                .to(OrderServiceChannels.COMMAND_CHANNEL)
                .build();
    }

    private CommandWithDestination beginReviseTicket(ReviseOrderSagaData data) {
        return send(new BeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedOrderLineItems()))
                .to(KitchenServiceChannels.COMMAND_CHANEL)
                .build();
    }

    private CommandWithDestination undoBeginReviseTicket(ReviseOrderSagaData data) {
        return send(new UndoBeginReviseTicketCommand(data.getRestaurantId(), data.getOrderId()))
                .to(KitchenServiceChannels.COMMAND_CHANEL)
                .build();
    }

    private CommandWithDestination reviseAuthorization(ReviseOrderSagaData data) {
        return send(new ReviseAuthorization(data.getConsumerId(), data.getOrderId(), data.getRevisedOrderTotal()))
                .to(AccountingServiceChannels.COMMAND_CHANNEL)
                .build();

    }

    private CommandWithDestination confirmTicketRevision(ReviseOrderSagaData data) {
        return send(new ConfirmReviseTicketCommand(data.getRestaurantId(), data.getOrderId(), data.getOrderRevision().getRevisedOrderLineItems()))
                .to(KitchenServiceChannels.COMMAND_CHANEL)
                .build();
    }

    private CommandWithDestination confirmOrderRevision(ReviseOrderSagaData data) {
        return send(new ConfirmReviseOrderCommand(data.getOrderId(), data.getOrderRevision()))
                .to(OrderServiceChannels.COMMAND_CHANNEL)
                .build();
    }
}
