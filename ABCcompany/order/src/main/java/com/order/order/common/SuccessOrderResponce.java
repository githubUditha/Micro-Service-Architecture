package com.order.order.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.order.order.dto.OrderDTO;
import lombok.Getter;

@Getter
public class SuccessOrderResponce implements OrderResponce{
    @JsonUnwrapped
    private final OrderDTO order;

    public SuccessOrderResponce(OrderDTO order) {
        this.order = order;
    }
}
