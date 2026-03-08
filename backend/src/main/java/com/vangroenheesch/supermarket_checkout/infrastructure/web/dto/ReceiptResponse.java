package com.vangroenheesch.supermarket_checkout.infrastructure.web.dto;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Receipt;

/**
 * Response object for a receipt
 *
 * @param lines number of individual lines
 * @param total total amount
 * @param saved total amount saved
 */
public record ReceiptResponse(List<ReceiptLineResponse> lines, BigDecimal total, BigDecimal saved) {

  public static ReceiptResponse from(Receipt receipt) {
    List<ReceiptLineResponse> responseLines =
        receipt.lines().stream().map(ReceiptLineResponse::from).toList();
    return new ReceiptResponse(responseLines, receipt.total(), receipt.saved());
  }
}
