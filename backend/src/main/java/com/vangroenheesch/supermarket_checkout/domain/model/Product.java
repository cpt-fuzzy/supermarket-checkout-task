package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;

/**
 * A product available for purchase
 *
 * @param sku unique stock-keeping unit identifier
 * @param name human-readable product name
 * @param price unit price
 */
public record Product(String sku, String name, BigDecimal price) {
  public Product {
    if (sku == null || sku.isBlank()) throw new DomainValidationException("SKU must not be empty");
    if (name == null || name.isBlank())
      throw new DomainValidationException("Name must not be empty");
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
      throw new DomainValidationException("Price must be non-negative");
  }
}
