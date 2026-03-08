package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
class OfferEntity {

  @Id private String id;
  private String productSku;

  @Enumerated(EnumType.STRING)
  private Offer.OfferType type;

  private int requiredQuantity;

  @Column(precision = 10, scale = 2)
  private BigDecimal offerPrice;

  protected OfferEntity() {}

  public OfferEntity(
      String id,
      String productSku,
      Offer.OfferType type,
      int requiredQuantity,
      BigDecimal offerPrice) {
    this.id = id;
    this.productSku = productSku;
    this.type = type;
    this.requiredQuantity = requiredQuantity;
    this.offerPrice = offerPrice;
  }

  Offer toDomain() {
    return new Offer(id, productSku, type, requiredQuantity, offerPrice);
  }

  OfferEntity fromDomain(Offer offer) {
    return new OfferEntity(
        offer.id(), offer.productSku(), offer.type(), offer.requiredQuantity(), offer.offerPrice());
  }
}
