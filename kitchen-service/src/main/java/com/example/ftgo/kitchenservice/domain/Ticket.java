package com.example.ftgo.kitchenservice.domain;

import com.example.ftgo.common.UnsupportedStateTransitionException;
import com.example.ftgo.kitchenservice.api.TicketDetails;
import com.example.ftgo.kitchenservice.api.TicketLineItem;
import com.example.ftgo.kitchenservice.api.event.TicketAcceptedEvent;
import com.example.ftgo.kitchenservice.api.event.TicketDomainEvent;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Ticket {
    @Id
    private Long id;
    private TicketState state;
    private TicketState previousState;
    private Long restaurantId;

    @ElementCollection
    @CollectionTable(name = "ticket_line_items")
    private List<TicketLineItem> lineItems;

    private LocalDateTime readyBy;
    private LocalDateTime acceptTime;
    private LocalDateTime preparingTime;
    private LocalDateTime pickedUpTime;
    private LocalDateTime readyForPickupTime;

    public Ticket(Long restaurantId, Long id, TicketDetails details) {
        this.restaurantId = restaurantId;
        this.id = id;
        this.state = TicketState.CREATE_PENDING;
        this.lineItems = details.getLineItems();
    }

    public static ResultWithDomainEvents<Ticket, TicketDomainEvent> create(Long restaurantId, Long id, TicketDetails details) {
        return new ResultWithDomainEvents<>(new Ticket(restaurantId, id, details), Collections.singletonList(new TicketCreatedEvent(id, details)));
    }

    public List<TicketDomainEvent> confirmCreate() {
        switch (state) {
            case CREATE_PENDING -> {
                state = TicketState.AWAITING_ACCEPTANCE;
                return Collections.singletonList(new TicketCreatedEvent(id, new TicketDetails()));
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<TicketDomainEvent> cancelCreate() {
        switch (state) {
            case CREATE_PENDING -> {
                state = TicketState.CANCELED;
                return Collections.emptyList();
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<TicketDomainEvent> accept(LocalDateTime readyBy) {
        switch (state) {
            case AWAITING_ACCEPTANCE -> {
                this.acceptTime = LocalDateTime.now();
                if (!acceptTime.isBefore(readyBy)) {
                    throw new IllegalArgumentException(String.format("readyBy %s is not after now %s", readyBy, acceptTime));
                }
                return Collections.singletonList(new TicketAcceptedEvent(readyBy));
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<TicketPreparationStartedEvent> preparing() {
        switch (state) {
            case ACCEPTED -> {
                this.state = TicketState.PREPARING;
                this.preparingTime = LocalDateTime.now();
                return Collections.singletonList(new TicketPreparationStartedEvent());
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

    public List<TicketDomainEvent> cancel() {
        switch (state) {
            case AWAITING_ACCEPTANCE, ACCEPTED -> {
                this.previousState = state;
                this.state = TicketState.CANCELED_PENDING;
                return Collections.emptyList();
            }
            default -> throw new UnsupportedStateTransitionException(state.name());
        }
    }

}
