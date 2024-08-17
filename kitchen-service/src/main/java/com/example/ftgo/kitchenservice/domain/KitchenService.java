package com.example.ftgo.kitchenservice.domain;

import com.example.ftgo.kitchenservice.api.TicketDetails;
import com.example.ftgo.kitchenservice.api.event.TicketDomainEvent;
import com.example.ftgo.restaurantservice.api.RestaurantMenu;
import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final TicketRepository ticketRepository;
    private final TicketDomainEventPublisher domainEventPublisher;

    @Transactional
    public void accept(Long ticketId, LocalDateTime readyBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<TicketDomainEvent> events = ticket.accept(readyBy);
        domainEventPublisher.publish(ticket, events);
    }

    public Ticket createTicket(Long restaurantId, Long ticketId, TicketDetails ticketDetails) {
        ResultWithDomainEvents<Ticket, TicketDomainEvent> resultWithEvents = Ticket.create(restaurantId, ticketId, ticketDetails);
        ticketRepository.save(resultWithEvents.result);
        domainEventPublisher.publish(resultWithEvents.result, resultWithEvents.events);

        return resultWithEvents.result;
    }

    public void confirmCreateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<TicketDomainEvent> events = ticket.confirmCreate();
        domainEventPublisher.publish(ticket, events);
    }

    public void cancelCreateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<TicketDomainEvent> events = ticket.cancelCreate();
        domainEventPublisher.publish(ticket, events);
    }

    public void createMenu() {

    }

    public void reviseMenu(Long id, RestaurantMenu menu) {

    };
}
