package com.vangroenheesch.supermarket_checkout.application.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import com.vangroenheesch.supermarket_checkout.domain.port.OfferRepositoryPort;
import com.vangroenheesch.supermarket_checkout.domain.port.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GetProductCatalogHandler")
class GetProductCatalogHandlerTest {

  private ProductRepositoryPort productRepository;
  private OfferRepositoryPort offerRepository;
  private GetProductCatalogHandler handler;

  private static final Product APPLE = new Product("apple", "Apple", new BigDecimal("0.30"));
  private static final Product ORANGE = new Product("orange", "Orange", new BigDecimal("0.60"));

  private static final Offer OFFER_APPLE =
      new Offer("offer-apple", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45"));

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepositoryPort.class);
    offerRepository = mock(OfferRepositoryPort.class);
    handler = new GetProductCatalogHandler(productRepository, offerRepository);
  }

  @Test
  @DisplayName("returns products paired with their matching offers")
  void returnsProductsWithOffer() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE, ORANGE));
    when(offerRepository.findAll()).thenReturn(List.of(OFFER_APPLE));

    var catalog = handler.getProductCatalog();

    assertAll(
        () -> assertEquals(2, catalog.size()),
        () -> assertEquals(APPLE, catalog.getFirst().product()),
        () -> assertEquals(OFFER_APPLE, catalog.getFirst().offer()),
        () -> assertEquals(ORANGE, catalog.get(1).product()),
        () -> assertNull(catalog.get(1).offer()));
  }

  @Test
  @DisplayName("returns products with null offers when no offers exist")
  void returnsProductsWithoutOffers() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE, ORANGE));
    when(offerRepository.findAll()).thenReturn(List.of());

    var catalog = handler.getProductCatalog();

    assertAll(
        () -> assertEquals(2, catalog.size()),
        () -> assertNull(catalog.getFirst().offer()),
        () -> assertNull(catalog.get(1).offer()));
  }

  @Test
  @DisplayName("returns empty list when no products exist")
  void returnsEmptyListWhenNoProducts() {
    when(productRepository.findAll()).thenReturn(List.of());
    when(offerRepository.findAll()).thenReturn(List.of());

    var catalog = handler.getProductCatalog();

    assertTrue(catalog.isEmpty());
  }
}
