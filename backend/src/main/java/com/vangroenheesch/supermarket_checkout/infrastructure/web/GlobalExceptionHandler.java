package com.vangroenheesch.supermarket_checkout.infrastructure.web;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import com.vangroenheesch.supermarket_checkout.domain.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  Map<String, Object> handleProductNotFound(ProductNotFoundException ex) {
    return Map.of("error", ex.getMessage());
  }

  @ExceptionHandler(DomainValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  Map<String, Object> handleDomainValidation(DomainValidationException ex) {
    return Map.of("error", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
    List<String> details =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .toList();
    return Map.of("error", "Validation failed", "details", details);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  Map<String, Object> handleUnreadableMessage(HttpMessageNotReadableException ex) {
    return Map.of("error", "Malformed request body");
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  Map<String, Object> handleUnexpected(Exception ex) {
    log.error("Unexpected error", ex);
    return Map.of("error", "An unexpected error occured");
  }
}
