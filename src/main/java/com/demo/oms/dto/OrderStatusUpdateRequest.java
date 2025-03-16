package com.demo.oms.dto;

import com.demo.oms.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class OrderStatusUpdateRequest {
    public static final String ORDER_STATUS_CANNOT_BE_NULL = "Order status cannot be null";
    @NotNull(message = ORDER_STATUS_CANNOT_BE_NULL)
    private OrderStatus status;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}

