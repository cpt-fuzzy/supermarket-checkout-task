package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

/**
 * Result of a checkout calculation. Contains individual line items
 *
 * @param lines receipt line items; must not be {@code null}
 * @param total total amount to pay; must be positive
 * @param saved total amount saved through offers; must be non-negative
 */
public record Receipt(List<ReceiptLine> lines, BigDecimal total, BigDecimal saved) {
  public Receipt {
    if (lines == null) throw new IllegalArgumentException("Receipt lines must not be null");
    if (total == null || total.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Total must not be non-negative");
    if (saved == null || saved.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Saved must not be non-negative");

    lines = List.copyOf(lines);
  }
}
