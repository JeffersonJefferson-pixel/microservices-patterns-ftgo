package com.example.ftgo.orderservice.domain;

import com.example.ftgo.orderservice.api.OrderDetails;
import com.example.ftgo.orderservice.api.OrderLineItem;
import com.example.ftgo.orderservice.api.events.OrderDomainEvent;
import com.example.ftgo.orderservice.saga.create_order.CreateOrderSaga;
import com.example.ftgo.orderservice.saga.create_order.CreateOrderSagaState;
import com.example.ftgo.orderservice.saga.revise_order.ReviseOrderSaga;
import com.example.ftgo.orderservice.saga.revise_order.ReviseOrderSagaData;
import com.example.ftgo.orderservice.web.MenuItemIdAndQuantity;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final SagaInstanceFactory sagaInstanceFactory;
    private final CreateOrderSaga createOrderSaga;
    private final ReviseOrderSaga reviseOrderSaga;
    private final OrderRepository orderRepository;
    private final OrderDomainEventPublisher eventPublisher;
    private final RestaurantRepository restaurantRepository;

    public Order createOrder(Long consumerId, Long restaurantId, DeliveryInformation deliveryInformation, List<MenuItemIdAndQuantity> lineItems) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

        List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems, restaurant);


        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = Order.createOrder(consumerId, restaurant, deliveryInformation,
                orderLineItems);

        Order order = orderAndEvents.result;
        // persist the order in database
        orderRepository.save(order);

        // publish domain even
        eventPublisher.publish(order, orderAndEvents.events);

        OrderDetails orderDetails = new OrderDetails(orderLineItems, order.getOrderTotal(), consumerId, restaurantId);

        // create a CreateOrderSaga
        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);

        return order;
    }

    public Order reviseOrder(Long orderId, OrderRevision orderRevision) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        ReviseOrderSagaData data = new ReviseOrderSagaData(orderId, orderRevision,  null, null, order.getConsumerId());
        sagaInstanceFactory.create(reviseOrderSaga, data);

        return order;
    }

    private List<OrderLineItem> makeOrderLineItems(List<MenuItemIdAndQuantity> lineItems, Restaurant restaurant) {
        return lineItems.stream().map(item -> {
            MenuItem menuItem = restaurant.findMenuItem(item.getMenuItemId()).orElseThrow(() -> new InvalidMenuItemIdException(item.getMenuItemId()));
            return new OrderLineItem(item.getQuantity(), item.getMenuItemId(), menuItem.getName(), menuItem.getPrice());
        }).toList();
    }

    public void approveOrder(long orderId) {
    }

    public void rejectOrder(Long orderId) {
    }

}
