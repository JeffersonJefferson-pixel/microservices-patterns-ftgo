package com.example.ftgo.orderservice.domain;

import com.example.ftgo.common.Money;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class MenuItem {
    private String id;
    private String name;
    private Money price;
}
