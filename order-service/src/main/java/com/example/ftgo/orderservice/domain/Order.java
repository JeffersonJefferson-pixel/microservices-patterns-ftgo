package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Money;
import com.example.ftgo.common.UnsupportedStateTransitionException;
import com.example.ftgo.orderservice.api.OrderDetails;
import com.example.ftgo.orderservice.api.OrderLineItem;
import com.example.ftgo.orderservice.api.OrderState;
import com.example.ftgo.orderservice.api.events.OrderAuthorized;
import com.example.ftgo.orderservice.api.events.OrderCreatedEvent;
import com.example.ftgo.orderservice.api.events.OrderDomainEvent;
import com.example.ftgo.orderservice.api.events.OrderRejected;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.example.ftgo.orderservice.api.OrderState.APPROVED;

@Getter
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private OrderState state;
    private Long consumerId;
    private Long restaurantId;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;

    @Embedded
    private Money orderMinimum;

    public Order(Long consumerId, Long restaurantId, DeliveryInformation deliveryInformation, List<OrderLineItem> orderLineItems) {
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.deliveryInformation = deliveryInformation;
        this.orderLineItems = new OrderLineItems(orderLineItems);
        this.state = OrderState.APPROVAL_PENDING;
    }

    public static ResultWithDomainEvents<Order, OrderDomainEvent> createOrder(Long consumerId, Restaurant restaurant, DeliveryInformation deliveryInformation, List<OrderLineItem> orderLineItems) {
        Order order = new Order(consumerId, restaurant.getId(), deliveryInformation, orderLineItems);
        OrderDetails orderDetails = new OrderDetails(orderLineItems, order.getOrderTotal(), restaurant.getId(), consumerId);
        List<OrderDomainEvent> events = Collections.singletonList(new OrderCreatedEvent(orderDetails, deliveryInformation.getDeliveryAddress(), restaurant.getName()));
        return new ResultWithDomainEvents<>(order, events);
    };

    public List<OrderDomainEvent> noteApproved() {
        switch(state) {
            case APPROVAL_PENDING -> {
                this.state = APPROVED;
                return Collections.singletonList(new OrderAuthorized());
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<OrderDomainEvent> noteRejected() {
        switch (state) {
            case APPROVAL_PENDING -> {
                this.state = OrderState.REJECTED;
                return Collections.singletonList(new OrderRejected());
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<OrderDomainEvent> revise(OrderRevision orderRevision) throws OrderMinimumNotMetException {
        switch (state) {
            case APPROVED -> {
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
                if (change.getNewOrderTotal().isGreaterThanOrEqual(orderMinimum)) {
                    throw new OrderMinimumNotMetException();
                }
                this.state = OrderState.REVISION_PENDING;
                return Collections.singletonList(new OrderRevisionProposed(orderRevision, change.getCurrentOrderTotal(), change.getNewOrderTotal()));
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<OrderDomainEvent> confirmRevision(OrderRevision orderRevision) {
        switch (state) {
            case REVISION_PENDING -> {
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);

                orderRevision.getDeliveryInformation()
                        .ifPresent(deliveryInformation -> this.deliveryInformation = deliveryInformation);

                if (!orderRevision.getRevisedOrderLineItems().isEmpty()) {
                    orderLineItems.updateLineItems(orderRevision);
                }
                this.state = APPROVED;
                return Collections.singletonList(new OrderRevised(orderRevision, change.getCurrentOrderTotal(), change.getNewOrderTotal()));
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public Money getOrderTotal() {
        return orderLineItems.getOrderTotal();
    }
}
