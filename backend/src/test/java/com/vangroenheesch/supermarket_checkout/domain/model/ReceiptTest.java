package com.vangroenheesch.supermarket_checkout.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Receipt")
class ReceiptTest {

  private static final ReceiptLine LINE_MOCK =
      new ReceiptLine(
          "Apple", 2, new BigDecimal("0.30"), new BigDecimal("0.60"), null, BigDecimal.ZERO);

  @Test
  void validReceiptIsCreated() {
    var receipt = new Receipt(List.of(LINE_MOCK), new BigDecimal("0.60"), BigDecimal.ZERO);

    assertAll(
        () -> assertEquals(1, receipt.lines().size()),
        () -> assertEquals(new BigDecimal("0.60"), receipt.total()),
        () -> assertEquals(BigDecimal.ZERO, receipt.saved()));
  }

  @Test
  void linesListIsImmutable() {
    var receipt = new Receipt(List.of(LINE_MOCK), new BigDecimal("0.60"), BigDecimal.ZERO);

    assertThrows(UnsupportedOperationException.class, () -> receipt.lines().add(LINE_MOCK));
  }

  @Test
  void rejectsNullLines() {
    assertThrows(
        DomainValidationException.class,
        () -> new Receipt(null, new BigDecimal("0.60"), BigDecimal.ZERO));
  }

  @Test
  void rejectsNegativeTotal() {
    assertThrows(
        DomainValidationException.class,
        () -> new Receipt(List.of(LINE_MOCK), new BigDecimal("-1"), BigDecimal.ZERO));
  }

  @Test
  void rejectsNegativeSaved() {
    assertThrows(
        DomainValidationException.class,
        () -> new Receipt(List.of(LINE_MOCK), new BigDecimal("0.60"), new BigDecimal("-1")));
  }
}
