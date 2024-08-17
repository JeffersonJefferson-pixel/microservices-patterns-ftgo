package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Address;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeliveryInformation {
    private LocalDateTime deliveryTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="state", column = @Column(name = "delivery_state"))
    })
    private Address deliveryAddress;
}
