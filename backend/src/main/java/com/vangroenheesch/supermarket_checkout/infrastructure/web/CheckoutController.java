package com.vangroenheesch.supermarket_checkout.infrastructure.web;

import com.vangroenheesch.supermarket_checkout.application.checkout.CheckoutUseCase;
import com.vangroenheesch.supermarket_checkout.domain.model.Receipt;
import com.vangroenheesch.supermarket_checkout.infrastructure.web.dto.CheckoutRequest;
import com.vangroenheesch.supermarket_checkout.infrastructure.web.dto.ReceiptResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
class CheckoutController {

  private final CheckoutUseCase checkout;

  CheckoutController(CheckoutUseCase checkout) {
    this.checkout = checkout;
  }

  @PostMapping
  ReceiptResponse checkout(@Valid @RequestBody CheckoutRequest request) {
    Receipt receipt = checkout.checkout(request.toDomain());
    return ReceiptResponse.from(receipt);
  }
}
