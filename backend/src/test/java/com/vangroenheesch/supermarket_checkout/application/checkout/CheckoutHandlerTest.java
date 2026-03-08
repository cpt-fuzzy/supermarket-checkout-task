package com.vangroenheesch.supermarket_checkout.application.checkout;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import com.vangroenheesch.supermarket_checkout.domain.exception.ProductNotFoundException;
import com.vangroenheesch.supermarket_checkout.domain.model.Cart;
import com.vangroenheesch.supermarket_checkout.domain.model.CartItem;
import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import com.vangroenheesch.supermarket_checkout.domain.port.OfferRepositoryPort;
import com.vangroenheesch.supermarket_checkout.domain.port.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CheckoutHandler")
class CheckoutHandlerTest {

  private ProductRepositoryPort productRepository;
  private OfferRepositoryPort offerRepository;
  private CheckoutHandler handler;

  private static final Product APPLE = new Product("apple", "Apple", new BigDecimal("0.30"));
  private static final Product BANANA = new Product("banana", "Banana", new BigDecimal("0.35"));
  private static final Product ORANGE = new Product("orange", "Orange", new BigDecimal("0.60"));

  private static final Offer OFFER_APPLE =
      new Offer("offer-apple", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45"));
  private static final Offer OFFER_BANANA =
      new Offer("offer-banana", "banana", Offer.OfferType.N_FOR_PRICE, 3, new BigDecimal("1.00"));

  @BeforeEach
  void setUp() {
    productRepository = mock(ProductRepositoryPort.class);
    offerRepository = mock(OfferRepositoryPort.class);
    handler = new CheckoutHandler(productRepository, offerRepository);
  }

  @Test
  @DisplayName("returns receipt with offer applied for qualifying quantity")
  void returnsReceiptWithOfferApplied() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE, BANANA, ORANGE));
    when(offerRepository.findAll()).thenReturn(List.of(OFFER_APPLE, OFFER_BANANA));

    var cart = new Cart(List.of(new CartItem("apple", 2)));

    var receipt = handler.checkout(cart);

    assertAll(
        () -> assertEquals(1, receipt.lines().size()),
        () -> assertEquals(new BigDecimal("0.45"), receipt.total()),
        () -> assertEquals(new BigDecimal("0.15"), receipt.saved()),
        () -> assertEquals("2 for 0.45", receipt.lines().getFirst().offerDescription()));
  }

  @Test
  @DisplayName("returns receipt at regular prices when no offers exist")
  void returnsReceiptWithoutOffers() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE, BANANA, ORANGE));
    when(offerRepository.findAll()).thenReturn(List.of());

    var cart = new Cart(List.of(new CartItem("apple", 3)));

    var receipt = handler.checkout(cart);

    assertAll(
        () -> assertEquals(new BigDecimal("0.90"), receipt.total()),
        () -> assertEquals(new BigDecimal("0"), receipt.saved()),
        () -> assertNull(receipt.lines().getFirst().offerDescription()));
  }

  @Test
  @DisplayName("propagates ProductNotFoundException for unknown SKU")
  void throwsForUnknownSku() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE));
    when(offerRepository.findAll()).thenReturn(List.of());

    var cart = new Cart(List.of(new CartItem("unknown", 1)));

    var ex = assertThrows(ProductNotFoundException.class, () -> handler.checkout(cart));
    assertEquals("unknown", ex.getProductSku());
  }

  @Test
  @DisplayName("propagates DomainValidationException for empty cart")
  void throwsForEmptyCart() {
    when(productRepository.findAll()).thenReturn(List.of(APPLE));
    when(offerRepository.findAll()).thenReturn(List.of());

    var cart = new Cart(List.of());

    assertThrows(DomainValidationException.class, () -> handler.checkout(cart));
  }
}
