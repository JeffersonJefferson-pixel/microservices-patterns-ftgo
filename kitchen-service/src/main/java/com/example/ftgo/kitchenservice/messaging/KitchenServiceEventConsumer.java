package com.example.ftgo.kitchenservice.messaging;

import com.example.ftgo.kitchenservice.domain.KitchenService;
import com.example.ftgo.restaurantservice.api.RestaurantMenu;
import com.example.ftgo.restaurantservice.api.events.RestaurantMenuRevised;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenServiceEventConsumer {
    private final KitchenService kitchenService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder.forAggregateType("com.example.ftgo.restaurantservice.Restaurant")
                .onEvent(RestaurantMenuRevised.class, this::reviseMenu)
                .build();
    }

    public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevised> eventEnvelope) {
        long id = Long.parseLong(eventEnvelope.getAggregateId());
        RestaurantMenu revisedMenu = eventEnvelope.getEvent().getMenu();
        kitchenService.reviseMenu(id, revisedMenu);
    }
}
