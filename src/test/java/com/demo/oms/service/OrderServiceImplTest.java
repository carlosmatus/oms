package com.demo.oms.service;

import com.demo.oms.dto.OrderStatusUpdateRequest;
import com.demo.oms.entity.Order;
import com.demo.oms.entity.OrderStatus;
import com.demo.oms.exception.InvalidOrderException;
import com.demo.oms.exception.OrderNotFoundException;
import com.demo.oms.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CommerceToolService commerceToolService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @Test
    void testUpdateOrderStatus_OrderFound_Success() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setProductId(10L);
        order.setQuantity(2);
        order.setPrice(BigDecimal.valueOf(100.0));
        // Arrange
        OrderStatusUpdateRequest statusUpdateRequest = new OrderStatusUpdateRequest();
        statusUpdateRequest.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order updatedOrder = orderService.updateOrderStatus(1L, statusUpdateRequest);

        // Assert
        assertEquals(OrderStatus.SHIPPED, updatedOrder.getStatus());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }
    @Test
    void placeOrder_ShouldSaveAndReturnOrder() {
        when(commerceToolService.checkProductAvailability(10L,2)).thenReturn(true);
        Order order = new Order();
        order.setCustomerId(1L);
        order.setProductId(10L);
        order.setQuantity(2);
        order.setPrice(BigDecimal.valueOf(100.0));

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId(order.getCustomerId());
        savedOrder.setProductId(order.getProductId());
        savedOrder.setQuantity(order.getQuantity());
        savedOrder.setPrice(order.getPrice());

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.placeOrder(order);

        assertNotNull(result);
        assertEquals(order.getCustomerId(), result.getCustomerId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    @Test
    void testPlaceOrder_ProductNotFound_ShouldThrowException() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setProductId(10L);
        order.setQuantity(0);
        // Arrange
        when(commerceToolService.fetchProductDetails(100L)).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidOrderException.class, () -> orderService.placeOrder(order));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenExists() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderStatusUpdateRequest orderStatusUpdateRequest = new OrderStatusUpdateRequest();
        orderStatusUpdateRequest.setStatus(OrderStatus.SHIPPED);
        Order updatedOrder = orderService.updateOrderStatus(1L,orderStatusUpdateRequest );

        assertEquals(OrderStatus.SHIPPED, updatedOrder.getStatus());
    }

    @Test
    void getOrdersByCustomerId_ShouldReturnList() {
        List<Order> orders = List.of(new Order(), new Order());

        when(orderRepository.findByCustomerId(1L)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCustomerId(1L);

        assertEquals(2, result.size());
    }
}

