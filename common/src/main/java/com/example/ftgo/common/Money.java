package com.example.ftgo.common;

import java.math.BigDecimal;

public class Money {
    public static Money ZERO = new Money(0);
    private BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(int amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money add(Money delta) {
        return new Money(amount.add(delta.amount));
    }

    public Money multiply(int x) {
        return new Money(amount.multiply(new BigDecimal(x)));
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }
}
