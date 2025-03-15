package com.demo.oms.controller;

import com.demo.oms.dto.OrderRequest;
import com.demo.oms.dto.OrderStatusUpdateRequest;
import com.demo.oms.entity.Order;
import com.demo.oms.entity.OrderStatus;
import com.demo.oms.service.OrderService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Received order request: {}", orderRequest);
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderService.placeOrder(order);
        logger.info("Order placed successfully: {}", savedOrder.getId());
        return ResponseEntity.status(201).body(savedOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        logger.debug("Fetching order by ID: {}", orderId);
        Optional<Order> order = Optional.of(orderService.getOrderById(orderId));
        logger.info("Fetched order: {}", orderId);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody OrderStatus status) {
        logger.info("Updating order status for orderId: {} to status: {}", orderId, status);
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest();
        orderStatusUpdateRequest.setStatus(status);
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatusUpdateRequest);
        logger.info("Order status updated for orderId: {}", orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        logger.debug("Fetching orders for customerId: {}", customerId);
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        logger.info("Found {} orders for customerId: {}", orders.size(), customerId);
        return ResponseEntity.ok(orders);
    }
}
