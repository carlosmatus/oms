package com.demo.oms.service;


import com.demo.oms.dto.ProductDetails;

public interface CommerceToolService {

    /**
     * Checks if the product is available in the given quantity.
     *
     * @param productId The product ID to check.
     * @param quantity  The requested quantity.
     * @return true if available; false otherwise.
     */
    boolean checkProductAvailability(Long productId, int quantity);

    /**
     * Fetches product details from the external service.
     *
     * @param productId The product ID.
     * @return Product details (name, price, etc.).
     */
    ProductDetails fetchProductDetails(Long productId);
}

