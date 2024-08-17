package com.example.ftgo.orderservice.api;

import com.example.ftgo.common.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    private int quantity;
    private String menuItemId;
    private String name;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name="amount", column=@Column(name="price")))
    private Money price;

    public Money getTotal() {
        return price.multiply(quantity);
    }

    public Money deltaForChangedQuantity(int newQuantity) {
        return price.multiply(newQuantity - quantity);
    }
}
