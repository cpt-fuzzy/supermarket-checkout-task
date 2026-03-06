package com.vangroenheesch.supermarket_checkout.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Named.named;

import module java.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Offer")
class OfferTest {

  @Test
  void validOfferIsCreated() {
    var offer = new Offer("o1", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45"));

    assertAll(
        () -> assertEquals("o1", offer.id()),
        () -> assertEquals("apple", offer.productSku()),
        () -> assertEquals(Offer.OfferType.N_FOR_PRICE, offer.type()),
        () -> assertEquals(2, offer.requiredQuantity()),
        () -> assertEquals(new BigDecimal("0.45"), offer.offerPrice()));
  }

  @ParameterizedTest
  @MethodSource("invalidOffers")
  void rejectsInvalidConstructorArguments(Executable constructor) {
    assertThrows(IllegalArgumentException.class, constructor);
  }

  static Stream<Arguments> invalidOffers() {
    return Stream.of(
        Arguments.of(
            named(
                "blank id",
                (Executable)
                    () ->
                        new Offer(
                            "", "apple", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45"))),
            Arguments.of(
                named(
                    "null product SKU",
                    (Executable)
                        () ->
                            new Offer(
                                "o1",
                                null,
                                Offer.OfferType.N_FOR_PRICE,
                                2,
                                new BigDecimal("0.45")))),
            Arguments.of(
                named(
                    "blank product SKU",
                    (Executable)
                        () ->
                            new Offer(
                                "o1", "", Offer.OfferType.N_FOR_PRICE, 2, new BigDecimal("0.45")))),
            Arguments.of(
                named(
                    "null type",
                    (Executable) () -> new Offer("o1", "apple", null, 2, new BigDecimal("0.45")))),
            Arguments.of(
                named(
                    "required quantity less than 2",
                    (Executable)
                        () ->
                            new Offer(
                                "o1",
                                "apple",
                                Offer.OfferType.N_FOR_PRICE,
                                1,
                                new BigDecimal("0.45")))),
            Arguments.of(
                named(
                    "negative offer price",
                    (Executable)
                        () ->
                            new Offer(
                                "o1",
                                "apple",
                                Offer.OfferType.N_FOR_PRICE,
                                2,
                                new BigDecimal("-1"))))));
  }

  @Test
  void acceptsZeroOfferPrice() {
    var offer = new Offer("o1", "apple", Offer.OfferType.N_FOR_PRICE, 2, BigDecimal.ZERO);
    assertEquals(BigDecimal.ZERO, offer.offerPrice());
  }
}
