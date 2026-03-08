package com.vangroenheesch.supermarket_checkout.application.product;

import module java.base;

public interface GetProductCatalogUseCase {

  /**
   * Finds all products
   *
   * @return list of all products
   */
  List<ProductWithOfferDto> getProductCatalog();
}
