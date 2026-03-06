package com.vangroenheesch.supermarket_checkout.domain.port;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;

public interface OfferRepositoryPort {
  /**
   * Finds an active offer by id
   *
   * @param id offer identifier
   * @return the offer, or empty if not found
   */
  Optional<Offer> findActiveOfferById(String id);

  /**
   * Finds an active offer by productSku
   *
   * @param productSku SKU of the product
   * @return the offer, or empty if not found
   */
  Optional<Offer> findActiveOfferByProductSku(String productSku);

  /**
   * Finds all active offers
   *
   * @return all active offers
   */
  List<Offer> findAllActiveOffers();
}
