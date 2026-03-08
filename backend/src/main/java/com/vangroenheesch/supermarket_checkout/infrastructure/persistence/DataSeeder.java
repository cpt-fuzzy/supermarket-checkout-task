package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DataSeeder implements CommandLineRunner {

  private final JpaProductRepository productRepository;
  private final JpaOfferRepository offerRepository;

  DataSeeder(JpaProductRepository productRepository, JpaOfferRepository offerRepository) {
    this.productRepository = productRepository;
    this.offerRepository = offerRepository;
  }

  @Override
  @Transactional
  public void run(String... args) {
    if (productRepository.count() > 0) {
      return;
    }
    productRepository.saveAll(
        List.of(
            new ProductEntity("apple", "Apple", new BigDecimal("0.30")),
            new ProductEntity("banana", "Banana", new BigDecimal("0.50")),
            new ProductEntity("orange", "Orange", new BigDecimal("0.60")),
            new ProductEntity("milk", "Milk", new BigDecimal("1.20")),
            new ProductEntity("bread", "Bread", new BigDecimal("0.80"))));

    if (offerRepository.count() > 0) {
      return;
    }

    offerRepository.saveAll(
        List.of(
            new OfferEntity(
                "offer-apple", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45")),
            new OfferEntity(
                "offer-banana", "banana", Offer.OfferType.N_FOR_PRICE, 3, new BigDecimal("1.00")),
            new OfferEntity(
                "offer-bread", "bread", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("1.20"))));
  }
}
