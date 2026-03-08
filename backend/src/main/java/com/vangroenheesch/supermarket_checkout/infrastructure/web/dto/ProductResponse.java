package com.vangroenheesch.supermarket_checkout.infrastructure.web.dto;

import com.vangroenheesch.supermarket_checkout.application.product.ProductWithOfferDto;
import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import java.math.BigDecimal;

public record ProductResponse(String sku, String name, BigDecimal price, OfferInfo offer) {

  public record OfferInfo(int requiredQuantity, BigDecimal offerPrice, String description) {}

  public static ProductResponse from(ProductWithOfferDto dto) {
    OfferInfo offerInfo = null;
    Offer offer = dto.offer();
    if (offer != null) {
      offerInfo =
          new OfferInfo(
              offer.requiredQuantity(),
              offer.offerPrice(),
              offer.requiredQuantity() + " for " + offer.offerPrice());
    }
    return new ProductResponse(
        dto.product().sku(), dto.product().name(), dto.product().price(), offerInfo);
  }
}
