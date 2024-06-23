package com.example.microservices_patterns_ftgo.domain;

import com.example.microservices_patterns_ftgo.api.OrderDetails;
import com.example.microservices_patterns_ftgo.saga.CreateOrderSaga;
import com.example.microservices_patterns_ftgo.saga.CreateOrderSagaState;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final SagaInstanceFactory sagaInstanceFactory;
    private final CreateOrderSaga createOrderSaga;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher eventPublisher;

    public Order createOrder(OrderDetails orderDetails) {
        ResultWithEvents<Order> orderAndEvents = Order.createOrder();
        Order order = orderAndEvents.result;
        // persist the order in database
        orderRepository.save(order);

        // publish domain even
        eventPublisher.publish(Order.class, Long.toString(order.getId()), orderAndEvents.events);

        // create a CreateOrderSaga
        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);

        return order;
    }

    public void approveOrder(long orderId) {
    }

    public void rejectOrder(Long orderId) {
    }

}
