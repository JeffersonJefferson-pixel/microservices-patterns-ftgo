package com.example.ftgo.kitchenservice.domain;

import com.example.ftgo.common.Money;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MenuItem {
    private String id;
    private String name;
    private Money price;
}
