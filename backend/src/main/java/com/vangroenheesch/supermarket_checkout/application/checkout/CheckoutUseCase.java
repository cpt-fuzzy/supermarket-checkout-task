package com.vangroenheesch.supermarket_checkout.application.checkout;

import com.vangroenheesch.supermarket_checkout.domain.model.Cart;
import com.vangroenheesch.supermarket_checkout.domain.model.Receipt;

public interface CheckoutUseCase {
  /**
   * Calculates the receipt for the given cart
   *
   * @param cart cart received in a checkout request
   * @return finished receipt for cart
   */
  Receipt checkout(Cart cart);
}
