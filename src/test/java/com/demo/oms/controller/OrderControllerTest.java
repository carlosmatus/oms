package com.demo.oms.controller;

import com.demo.oms.dto.OrderRequest;
import com.demo.oms.dto.OrderStatusUpdateRequest;
import com.demo.oms.entity.Order;
import com.demo.oms.entity.OrderStatus;
import com.demo.oms.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_ShouldReturnCreatedOrder() {

        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        request.setProductId(10L);
        request.setQuantity(2);
        request.setPrice(BigDecimal.valueOf(100.0));

        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(request.getCustomerId());
        order.setQuantity(2);
        order.setProductId(10L);

        when(orderService.placeOrder(any())).thenReturn(order);

        ResponseEntity<Order> response = orderController.placeOrder(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(order.getId(), response.getBody().getId());
    }

    @Test
    void getOrderById_ShouldReturnOrder() {
        Order order = new Order();
        order.setId(1L);

        when(orderService.getOrderById(1L)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void updateOrderStatus_ShouldReturnUpdatedOrder() {
        Long orderId = 1L;

        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest();
        request.setStatus(OrderStatus.SHIPPED);

        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setStatus(OrderStatus.SHIPPED);

        when(orderService.updateOrderStatus(anyLong(), any())).thenReturn(updatedOrder);

        ResponseEntity<Order> response = orderController.updateOrderStatus(orderId, request.getStatus());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(OrderStatus.SHIPPED, response.getBody().getStatus());
    }

    @Test
    void getOrdersByCustomerId_ShouldReturnOrderList() {
        List<Order> orders = List.of(new Order(), new Order());

        when(orderService.getOrdersByCustomerId(1L)).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getOrdersByCustomerId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}

