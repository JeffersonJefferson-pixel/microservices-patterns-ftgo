package com.example.ftgo.orderservice.domain;

import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramEventsPublisherConfiguration.class, SagaOrchestratorConfiguration.class})
public class OrderServiceConfiguration {
}
