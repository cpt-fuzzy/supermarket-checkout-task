package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;

/**
 * A shopping cart containing items
 *
 * @param items cart items; must not be {@code null}
 */
public record Cart(List<CartItem> items) {
  public Cart {
    if (items == null) throw new DomainValidationException("Items must not be null");

    items = List.copyOf(items);
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
