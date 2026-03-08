package com.vangroenheesch.supermarket_checkout.application.checkout;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Cart;
import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import com.vangroenheesch.supermarket_checkout.domain.model.Receipt;
import com.vangroenheesch.supermarket_checkout.domain.port.OfferRepositoryPort;
import com.vangroenheesch.supermarket_checkout.domain.port.ProductRepositoryPort;
import com.vangroenheesch.supermarket_checkout.domain.service.PricingService;
import org.springframework.stereotype.Service;

@Service
class CheckoutHandler implements CheckoutUseCase {

  private final ProductRepositoryPort productRepository;
  private final OfferRepositoryPort offerRepository;
  private final PricingService pricingService = new PricingService();

  CheckoutHandler(ProductRepositoryPort productRepository, OfferRepositoryPort offerRepository) {
    this.productRepository = productRepository;
    this.offerRepository = offerRepository;
  }

  @Override
  public Receipt checkout(Cart cart) {
    Map<String, Product> productsBySku =
        productRepository.findAll().stream().collect(Collectors.toMap(Product::sku, p -> p));

    Map<String, Offer> offersByProductSku =
        offerRepository.findAll().stream().collect(Collectors.toMap(Offer::productSku, o -> o));

    return pricingService.calculateReceipt(cart, productsBySku, offersByProductSku);
  }
}
