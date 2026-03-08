package com.vangroenheesch.supermarket_checkout.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import com.vangroenheesch.supermarket_checkout.domain.exception.ProductNotFoundException;
import com.vangroenheesch.supermarket_checkout.domain.model.Cart;
import com.vangroenheesch.supermarket_checkout.domain.model.CartItem;
import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import org.junit.jupiter.api.*;

@DisplayName("PricingService")
class PricingServiceTest {

  private PricingService pricingService;

  private static final Product APPLE = new Product("apple", "Apple", new BigDecimal("0.30"));
  private static final Product BANANA = new Product("banana", "Banana", new BigDecimal("0.50"));
  private static final Product ORANGE = new Product("orange", "Orange", new BigDecimal("0.60"));

  private static final Offer APPLE_OFFER =
      new Offer("o1", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45"));
  private static final Offer BANANA_OFFER =
      new Offer("o2", "banana", Offer.OfferType.N_FOR_PRICE, 3, new BigDecimal("1.00"));

  private static final Map<String, Product> ALL_PRODUCTS =
      Map.of(
          "apple", APPLE,
          "banana", BANANA,
          "orange", ORANGE);

  private static final Map<String, Offer> ALL_OFFERS =
      Map.of(
          "apple", APPLE_OFFER,
          "banana", BANANA_OFFER);

  @BeforeEach
  void setUp() {
    pricingService = new PricingService();
  }

  @Nested
  @DisplayName("Without offers")
  class WithoutOffers {

    @Test
    void singleItemNoOffer() {
      var cart = new Cart(List.of(new CartItem("orange", 2)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, Map.of());

      assertEquals(1, receipt.lines().size());
      var line = receipt.lines().getFirst();
      assertAll(
          () -> assertEquals("Orange", line.productName()),
          () -> assertEquals(2, line.quantity()),
          () -> assertEquals(new BigDecimal("0.60"), line.unitPrice()),
          () -> assertEquals(new BigDecimal("1.20"), line.lineTotal()),
          () -> assertNull(line.offerDescription()),
          () -> assertEquals(BigDecimal.ZERO, line.lineSaved()),
          () -> assertEquals(new BigDecimal("1.20"), receipt.total()),
          () -> assertEquals(BigDecimal.ZERO, receipt.saved()));
    }

    @Test
    void multipleItemsNoOffers() {
      var cart = new Cart(List.of(new CartItem("orange", 1), new CartItem("banana", 2)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, Map.of());

      assertEquals(2, receipt.lines().size());
      assertEquals(new BigDecimal("1.60"), receipt.total());
      assertEquals(BigDecimal.ZERO, receipt.saved());
    }
  }

  @Nested
  @DisplayName("With offers")
  class WithOffers {

    @Test
    void offerAppliedExactly() {
      var cart = new Cart(List.of(new CartItem("apple", 2)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      var line = receipt.lines().getFirst();
      assertAll(
          () -> assertEquals(new BigDecimal("0.45"), line.lineTotal()),
          () -> assertEquals(new BigDecimal("0.15"), line.lineSaved()),
          () -> assertEquals("2 for 0.45", line.offerDescription()),
          () -> assertEquals(new BigDecimal("0.45"), receipt.total()),
          () -> assertEquals(new BigDecimal("0.15"), receipt.saved()));
    }

    @Test
    void offerAppliedWithRemainder() {
      var cart = new Cart(List.of(new CartItem("apple", 3)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      var line = receipt.lines().getFirst();
      assertEquals(new BigDecimal("0.75"), line.lineTotal());
      assertEquals(new BigDecimal("0.15"), line.lineSaved());
    }

    @Test
    void offerAppliedMultipleTimes() {
      var cart = new Cart(List.of(new CartItem("apple", 4)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      var line = receipt.lines().getFirst();
      assertEquals(new BigDecimal("0.90"), line.lineTotal());
      assertEquals(new BigDecimal("0.30"), line.lineSaved());
    }

    @Test
    void offerNotAppliedWhenQuantityTooLow() {
      var cart = new Cart(List.of(new CartItem("apple", 1)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      var line = receipt.lines().getFirst();
      assertEquals(new BigDecimal("0.30"), line.lineTotal());
      assertEquals(BigDecimal.ZERO, line.lineSaved());
      assertNull(line.offerDescription());
    }

    @Test
    void productWithOfferAndProductWithout() {
      var cart = new Cart(List.of(new CartItem("apple", 2), new CartItem("orange", 1)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      assertEquals(2, receipt.lines().size());
      assertEquals(new BigDecimal("1.05"), receipt.total());
      assertEquals(new BigDecimal("0.15"), receipt.saved());
    }

    @Test
    void multipleProductsWithOffers() {
      var cart = new Cart(List.of(new CartItem("apple", 2), new CartItem("banana", 3)));

      var receipt = pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS);

      assertEquals(new BigDecimal("1.45"), receipt.total());
      assertEquals(new BigDecimal("0.65"), receipt.saved());
    }
  }

  @Nested
  @DisplayName("Edge Cases")
  class EdgeCases {

    @Test
    void emptyCartThrows() {
      var cart = new Cart(List.of());

      assertThrows(
          DomainValidationException.class,
          () -> pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS));
    }

    @Test
    void unknownProductThrows() {
      var cart = new Cart(List.of(new CartItem("unknown", 1)));

      var exception =
          assertThrows(
              ProductNotFoundException.class,
              () -> pricingService.calculateReceipt(cart, ALL_PRODUCTS, ALL_OFFERS));
      assertEquals("unknown", exception.getProductSku());
    }
  }
}
