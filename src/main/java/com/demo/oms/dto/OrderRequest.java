package com.demo.oms.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
public class OrderRequest {

    public static final String CUSTOMER_ID_CANNOT_BE_NULL = "Customer ID cannot be null";
    public static final String PRODUCT_ID_CANNOT_BE_NULL = "Product ID cannot be null";
    @NotNull(message = CUSTOMER_ID_CANNOT_BE_NULL)
    private Long customerId;
    @NotNull(message = PRODUCT_ID_CANNOT_BE_NULL)
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

