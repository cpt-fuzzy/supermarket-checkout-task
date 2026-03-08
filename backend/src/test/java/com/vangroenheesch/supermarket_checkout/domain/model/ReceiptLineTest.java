package com.vangroenheesch.supermarket_checkout.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Named.named;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("ReceiptLine")
class ReceiptLineTest {

  @Test
  void validLineIsCreated() {
    var line =
        new ReceiptLine(
            "Apple",
            3,
            new BigDecimal("0.30"),
            new BigDecimal("0.75"),
            "2 for 0.45",
            new BigDecimal("0.15"));

    assertAll(
        () -> assertEquals("Apple", line.productName()),
        () -> assertEquals(3, line.quantity()),
        () -> assertEquals(new BigDecimal("0.30"), line.unitPrice()),
        () -> assertEquals(new BigDecimal("0.75"), line.lineTotal()),
        () -> assertEquals("2 for 0.45", line.offerDescription()),
        () -> assertEquals(new BigDecimal("0.15"), line.lineSaved()));
  }

  @Test
  void acceptsNullOfferDescription() {
    var line =
        new ReceiptLine(
            "Apple",
            3,
            new BigDecimal("0.30"),
            new BigDecimal("0.75"),
            null,
            new BigDecimal("0.15"));

    assertNull(line.offerDescription());
  }

  @ParameterizedTest
  @MethodSource("invalidReceiptLines")
  void rejectsInvalidConstructorArguments(Executable constructor) {
    assertThrows(DomainValidationException.class, constructor);
  }

  static Stream<Arguments> invalidReceiptLines() {
    return Stream.of(
        Arguments.of(
            named(
                "blank product name",
                (Executable)
                    () ->
                        new ReceiptLine(
                            "", 1, BigDecimal.ONE, BigDecimal.ONE, null, BigDecimal.ZERO))),
        Arguments.of(
            named(
                "zero quantity",
                (Executable)
                    () ->
                        new ReceiptLine(
                            "Apple", 0, BigDecimal.ONE, BigDecimal.ONE, null, BigDecimal.ZERO))),
        Arguments.of(
            named(
                "negative unit price",
                (Executable)
                    () ->
                        new ReceiptLine(
                            "Apple",
                            1,
                            new BigDecimal("-1"),
                            BigDecimal.ONE,
                            null,
                            BigDecimal.ZERO))),
        Arguments.of(
            named(
                "negative line total",
                (Executable)
                    () ->
                        new ReceiptLine(
                            "Apple",
                            1,
                            BigDecimal.ONE,
                            new BigDecimal("-1"),
                            null,
                            BigDecimal.ZERO))),
        Arguments.of(
            named(
                "negative line saved",
                (Executable)
                    () ->
                        new ReceiptLine(
                            "Apple",
                            1,
                            BigDecimal.ONE,
                            BigDecimal.ONE,
                            null,
                            new BigDecimal("-1")))));
  }
}
