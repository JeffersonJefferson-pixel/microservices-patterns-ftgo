package com.example.ftgo.orderservice.saga.revise_order;

import com.example.ftgo.common.Money;
import com.example.ftgo.orderservice.domain.OrderRevision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviseOrderSagaData {
    private Long orderId;
    private OrderRevision orderRevision;
    private Money revisedOrderTotal;
    private Long restaurantId;
    private Long consumerId;
}
