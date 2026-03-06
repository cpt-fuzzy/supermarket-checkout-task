package com.vangroenheesch.supermarket_checkout.domain.model;

import module java.base;

/**
 * Individual line on a Receipt
 *
 * @param productName name of the product
 * @param quantity number of units purchased; must be at least 1
 * @param unitPrice regular per-unit price; must be non-negative
 * @param lineTotal total charged for this line after any offers; must be non-negative
 * @param offerDescription human-readable description of the applied offer, or {@code null}
 * @param lineSaved amount saved by the offer; must be non-negative
 */
public record ReceiptLine(
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal lineTotal,
    String offerDescription,
    BigDecimal lineSaved) {
  public ReceiptLine {
    if (productName == null || productName.isBlank())
      throw new IllegalArgumentException("Product name must not be empty");
    if (quantity < 1) throw new IllegalArgumentException("Quantity must at least be 1");
    if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Unit price must be non-negative");
    if (lineTotal == null || lineTotal.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Line total must be non-negative");
    if (lineSaved == null || lineSaved.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Line saved must be non-negative");
  }
}
