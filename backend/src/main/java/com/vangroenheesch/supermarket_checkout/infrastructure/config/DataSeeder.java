package com.vangroenheesch.supermarket_checkout.infrastructure.config;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.infrastructure.persistence.JpaOfferRepository;
import com.vangroenheesch.supermarket_checkout.infrastructure.persistence.JpaProductRepository;
import com.vangroenheesch.supermarket_checkout.infrastructure.persistence.OfferEntity;
import com.vangroenheesch.supermarket_checkout.infrastructure.persistence.ProductEntity;
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
  public void run(String... args) {
    productRepository.saveAll(
        List.of(
            new ProductEntity("apple", "Apple", new BigDecimal("0.30")),
            new ProductEntity("banana", "Banana", new BigDecimal("0.50")),
            new ProductEntity("orange", "Orange", new BigDecimal("0.60")),
            new ProductEntity("milk", "Milk", new BigDecimal("1.20")),
            new ProductEntity("bread", "Bread", new BigDecimal("0.80"))));

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
