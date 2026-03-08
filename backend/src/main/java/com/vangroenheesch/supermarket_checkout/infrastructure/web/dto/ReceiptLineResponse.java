package com.vangroenheesch.supermarket_checkout.infrastructure.web.dto;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.ReceiptLine;

/**
 * Response object of a receipt line
 *
 * @param productName Name of the product
 * @param quantity number of units
 * @param unitPrice price of individual unit
 * @param lineTotal total amount of line
 * @param offerDescription description of offer
 * @param lineSaved total amount saved of line
 */
public record ReceiptLineResponse(
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal lineTotal,
    String offerDescription,
    BigDecimal lineSaved) {

  public static ReceiptLineResponse from(ReceiptLine line) {
    return new ReceiptLineResponse(
        line.productName(),
        line.quantity(),
        line.unitPrice(),
        line.lineTotal(),
        line.offerDescription(),
        line.lineSaved());
  }
}
