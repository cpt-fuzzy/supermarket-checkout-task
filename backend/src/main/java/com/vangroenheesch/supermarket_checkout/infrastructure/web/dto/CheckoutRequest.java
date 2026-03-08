package com.vangroenheesch.supermarket_checkout.infrastructure.web.dto;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Cart;
import com.vangroenheesch.supermarket_checkout.domain.model.CartItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 * Request object of a checkout operation
 *
 * @param items List of items in checkout; must not be empty
 */
public record CheckoutRequest(@NotEmpty List<@Valid CheckoutItemRequest> items) {

  public Cart toDomain() {
    List<CartItem> cartItems =
        items().stream().map(i -> new CartItem(i.productSku(), i.quantity())).toList();
    return new Cart(cartItems);
  }
}
