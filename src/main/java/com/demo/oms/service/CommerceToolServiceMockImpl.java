package com.demo.oms.service;

import com.demo.oms.dto.ProductDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommerceToolServiceMockImpl implements CommerceToolService {

    /**
     * Checks if the product is available in the given quantity.
     * This is a mock implementation that always returns true.
     *
     * @param productId The product ID to check.
     * @param quantity  The requested quantity.
     * @return true if available; false otherwise.
     */
    @Override
    public boolean checkProductAvailability(Long productId, int quantity) {
        // Mock logic: Assume that all products are available in any quantity
        return true; // Change this based on your mock behavior
    }

    /**
     * Fetches product details from the external service.
     * This is a mock implementation that returns mock product details.
     *
     * @param productId The product ID.
     * @return Product details (name, price, etc.).
     */
    @Override
    public ProductDetails fetchProductDetails(Long productId) {
        // Mock logic: Return fake product details based on product ID
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(productId);
        productDetails.setProductName("Mock Product " + productId);
        productDetails.setPrice(new BigDecimal(99.99)); // Mock price

        return productDetails;
    }
}

