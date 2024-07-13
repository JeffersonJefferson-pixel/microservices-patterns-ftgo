package com.example.ftgo.orderservice.saga;

import com.example.ftgo.accountingservice.api.AuthorizeCommand;
import com.example.ftgo.consumerservice.api.ValidateOrderByConsumer;
import com.example.ftgo.kitchenservice.api.*;
import com.example.ftgo.orderservice.api.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class CreateOrderSagaState {
    private Long orderId;
    private OrderDetails orderDetails;
    private Long ticketId;

    public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    CreateTicket makeCreateTicketCommand() {
        return new CreateTicket(getOrderId(), makeTicketDetails(getOrderDetails()), getOrderDetails().getRestaurantId());
    }

    private TicketDetails makeTicketDetails(OrderDetails orderDetails) {
        return new TicketDetails();
    }

    void handleCreateTicketReply(CreateTicketReply reply) {
        log.debug("getTicketId {}", reply.getTicketId());
        setTicketId(reply.getTicketId());
    }

    CancelCreateTicket makeCancelCreateTicketCommand() {
        return new CancelCreateTicket(getOrderId());
    }

    RejectOrderCommand makeRejectOrderCommand() {
        return new RejectOrderCommand(getOrderId());
    }

    ValidateOrderByConsumer makeValidateOrderByConsumerCommand() {
        return new ValidateOrderByConsumer(getOrderDetails().getConsumerId(), getOrderId(), getOrderDetails().getOrderTotal());
    }

    AuthorizeCommand makeAuthorizeCommand() {
        return new AuthorizeCommand(getOrderDetails().getConsumerId(), getOrderId(), getOrderDetails().getOrderTotal());
    }

    ConfirmCreateTicket makeConfirmCreateTicketCommand() {
        return new ConfirmCreateTicket(getTicketId());
    }

    ApproveOrderCommand makeApproveOrderCommand() {
        return new ApproveOrderCommand(getOrderId());
    }

}
