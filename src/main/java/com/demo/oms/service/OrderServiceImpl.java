package com.demo.oms.service;

import com.demo.oms.dto.OrderStatusUpdateRequest;
import com.demo.oms.entity.Order;
import  com.demo.oms.exception.InvalidOrderException;
import  com.demo.oms.exception.OrderNotFoundException;
import com.demo.oms.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);
    public static final String ORDER_NOT_FOUND = "Order not found";

    private final OrderRepository orderRepository;
    private final CommerceToolService commerceToolService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CommerceToolService commerceToolService) {

        this.orderRepository = orderRepository;
        this.commerceToolService = commerceToolService;
    }

    @Override
    public Order placeOrder(Order order) {
        logger.debug("Checking product availability for productId: {}", order.getProductId());
        boolean isAvailable = commerceToolService.checkProductAvailability(order.getProductId(), order.getQuantity());

        if (!isAvailable) {
            logger.warn("Product {} not available in quantity {}", order.getProductId(), order.getQuantity());
            throw new InvalidOrderException("Product is not available in the requested quantity.");
        }

        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved with ID: {}", savedOrder.getId());
        return savedOrder;

    }

    @Override
    @Cacheable(value = "orders", key = "#orderId")
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Order order = getOrderById(orderId);
        order.setStatus(orderStatusUpdateRequest.getStatus());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}

