package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

/**
 * A weekly promotional offer
 *
 * @param id unique offer identifier
 * @param productSku SKU of the product this offer applies to
 * @param type type of the discount applied
 * @param requiredQuantity minimum number of items to activate offer
 * @param offerPrice discounted price for the items
 */
public record Offer(
    String id, String productSku, OfferType type, int requiredQuantity, BigDecimal offerPrice) {
  public enum OfferType {
    N_FOR_PRICE,
  }

  public Offer {
    if (id == null || id.isBlank()) throw new IllegalArgumentException("ID must not be empty");
    if (productSku == null || productSku.isBlank())
      throw new IllegalArgumentException("Product SKU must not be null");
    if (type == null) throw new IllegalArgumentException("Type must not be null");
    if (requiredQuantity <= 1) throw new IllegalArgumentException("Quantity must be at least 2");
    if (offerPrice == null || offerPrice.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Offer price must be non-negative");
  }
}
