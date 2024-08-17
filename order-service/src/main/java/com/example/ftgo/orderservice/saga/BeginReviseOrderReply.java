package com.example.ftgo.orderservice.saga;

import com.example.ftgo.common.Money;
import lombok.Getter;

@Getter
public class BeginReviseOrderReply {
    private Money revisedOrderTotal;
}
