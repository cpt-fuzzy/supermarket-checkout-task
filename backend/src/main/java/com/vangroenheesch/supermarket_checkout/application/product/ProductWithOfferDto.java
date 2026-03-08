package com.vangroenheesch.supermarket_checkout.application.product;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;

public record ProductWithOfferDto(Product product, Offer offer) {
  public ProductWithOfferDto {
    if (product == null) throw new IllegalArgumentException("Product must not be null");
  }
}
