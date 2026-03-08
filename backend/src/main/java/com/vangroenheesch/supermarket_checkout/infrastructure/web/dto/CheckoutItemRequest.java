package com.vangroenheesch.supermarket_checkout.infrastructure.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Request object of a checkout item
 *
 * @param productSku SKU of the product; may not be null
 * @param quantity number of units; must be at least one
 */
public record CheckoutItemRequest(@NotBlank String productSku, @Min(1) int quantity) {}
