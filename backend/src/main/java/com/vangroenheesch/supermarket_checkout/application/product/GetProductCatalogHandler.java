package com.vangroenheesch.supermarket_checkout.application.product;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import com.vangroenheesch.supermarket_checkout.domain.port.OfferRepositoryPort;
import com.vangroenheesch.supermarket_checkout.domain.port.ProductRepositoryPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
class GetProductCatalogHandler implements GetProductCatalogUseCase {

  private final ProductRepositoryPort productRepository;
  private final OfferRepositoryPort offerRepository;

  GetProductCatalogHandler(
      ProductRepositoryPort productRepository, OfferRepositoryPort offerRepository) {
    this.productRepository = productRepository;
    this.offerRepository = offerRepository;
  }

  @Override
  @Cacheable("productCatalog")
  public List<ProductWithOfferDto> getProductCatalog() {
    List<Product> products = productRepository.findAll();
    Map<String, Offer> offersByProductSku =
        offerRepository.findAll().stream().collect(Collectors.toMap(Offer::productSku, o -> o));

    return products.stream()
        .map(product -> new ProductWithOfferDto(product, offersByProductSku.get(product.sku())))
        .toList();
  }
}
