package com.demo.oms.service;

import com.demo.oms.dto.OrderStatusUpdateRequest;
import com.demo.oms.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);
    Order getOrderById(Long orderId);
    Order updateOrderStatus(Long orderId, OrderStatusUpdateRequest orderStatusUpdateRequest);
    List<Order> getOrdersByCustomerId(Long customerId);
}

