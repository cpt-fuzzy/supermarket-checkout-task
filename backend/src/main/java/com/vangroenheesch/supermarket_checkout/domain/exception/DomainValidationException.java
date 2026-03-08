package com.vangroenheesch.supermarket_checkout.domain.exception;

public class DomainValidationException extends RuntimeException {
  public DomainValidationException(String message) {
    super(message);
  }
}
