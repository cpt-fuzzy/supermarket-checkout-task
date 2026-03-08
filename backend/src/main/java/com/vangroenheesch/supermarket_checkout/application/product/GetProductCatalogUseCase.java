package com.vangroenheesch.supermarket_checkout.application.product;

import module java.base;

public interface GetProductCatalogUseCase {

  List<ProductWithOfferDto> getProductCatalog();
}
