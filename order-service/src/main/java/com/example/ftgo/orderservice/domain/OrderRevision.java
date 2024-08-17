package com.example.ftgo.orderservice.domain;

import com.example.ftgo.orderservice.api.RevisedOrderLineItem;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class OrderRevision {
    private Optional<DeliveryInformation> deliveryInformation = Optional.empty();
    private List<RevisedOrderLineItem> revisedOrderLineItems;
}
