package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

/**
 * A shopping cart containing items
 *
 * @param items cart items; must not be {@code null}
 */
public record Cart(List<CartItem> items) {
  public Cart {
    if (items == null) throw new IllegalArgumentException("Items must not be empty");
  }

  /**
   * Check if items is empty
   *
   * @return {@code true} if the cart contains zero items
   */
  public boolean isEmpty() {
    return items.isEmpty();
  }
}
