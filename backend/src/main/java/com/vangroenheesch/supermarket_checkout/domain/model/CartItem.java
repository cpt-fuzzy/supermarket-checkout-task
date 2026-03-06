package com.vangroenheesch.supermarket_checkout.domain.model;

/**
 * Individual item in a shopping cart
 *
 * @param productSku SKU of the product
 * @param quantity number of units; must be at least 1
 */
public record CartItem(String productSku, int quantity) {
  public CartItem {
    if (productSku == null || productSku.isBlank())
      throw new IllegalArgumentException("Product SKU must not be empty");
    if (quantity < 1) throw new IllegalArgumentException("Quantity must not be less than 1");
  }
}
