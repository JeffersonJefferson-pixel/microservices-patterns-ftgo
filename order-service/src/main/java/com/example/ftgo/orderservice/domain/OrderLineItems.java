package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Money;
import com.example.ftgo.orderservice.api.OrderLineItem;
import com.example.ftgo.orderservice.api.RevisedOrderLineItem;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItems {
    @ElementCollection
    @CollectionTable(name = "order_line_items")
    private List<OrderLineItem> lineItems;

    LineItemQuantityChange lineItemQuantityChange(OrderRevision orderRevision) {
        Money currentOrderTotal = getOrderTotal();
        Money delta = changeToOrderTotal(orderRevision);
        Money newOrderTotal = currentOrderTotal.add(delta);
        return new LineItemQuantityChange(currentOrderTotal, newOrderTotal, delta);
    }

    void updateLineItems(OrderRevision orderRevision) {
        lineItems.stream()
                .forEach(item -> {
                    Optional<Integer> revised = orderRevision.getRevisedOrderLineItems()
                            .stream().filter(revisedItem -> revisedItem.getMenuItemId().equals(item.getMenuItemId()))
                            .map(RevisedOrderLineItem::getQuantity)
                            .findFirst();
                    item.setQuantity(revised.orElseThrow(() -> new IllegalArgumentException(String.format("menu item id %s not found.", item.getMenuItemId()))));
                });
    }

    Money getOrderTotal() {
        return lineItems.stream().map(OrderLineItem::getTotal).reduce(Money.ZERO, Money::add);
    }

    private Money changeToOrderTotal(OrderRevision orderRevision) {
        return orderRevision.getRevisedOrderLineItems()
                .stream()
                .map(item -> {
                    OrderLineItem lineItem = findOrderLineItem(item.getMenuItemId());
                    return lineItem.deltaForChangedQuantity(item.getQuantity());
                })
                .reduce(Money.ZERO, Money::add);
    }

    private OrderLineItem findOrderLineItem(String lineItemId) {
        return lineItems.stream()
                .filter(item -> item.getMenuItemId().equals(lineItemId))
                .findFirst()
                .get();
    }
}
