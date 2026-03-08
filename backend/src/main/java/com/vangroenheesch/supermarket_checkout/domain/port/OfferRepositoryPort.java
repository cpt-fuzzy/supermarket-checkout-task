package com.vangroenheesch.supermarket_checkout.domain.port;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;

public interface OfferRepositoryPort {
  /**
   * Finds an offer by id
   *
   * @param id offer identifier
   * @return the offer, or empty if not found
   */
  Optional<Offer> findById(String id);

  /**
   * Finds an offer by productSku
   *
   * @param productSku SKU of the product
   * @return the offer, or empty if not found
   */
  Optional<Offer> findByProductSku(String productSku);

  /**
   * Finds all offers
   *
   * @return all offers
   */
  List<Offer> findAll();
}
