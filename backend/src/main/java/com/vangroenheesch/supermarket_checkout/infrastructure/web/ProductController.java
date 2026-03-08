package com.vangroenheesch.supermarket_checkout.infrastructure.web;

import module java.base;

import com.vangroenheesch.supermarket_checkout.application.product.GetProductCatalogUseCase;
import com.vangroenheesch.supermarket_checkout.infrastructure.web.dto.ProductResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {

  private static final Duration MAX_AGE = Duration.ofMinutes(5);

  private final GetProductCatalogUseCase getProductCatalog;

  ProductController(GetProductCatalogUseCase getProductCatalog) {
    this.getProductCatalog = getProductCatalog;
  }

  @GetMapping
  ResponseEntity<List<ProductResponse>> getProducts() {
    List<ProductResponse> products =
        getProductCatalog.getProductCatalog().stream().map(ProductResponse::from).toList();
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(MAX_AGE).cachePublic())
        .body(products);
  }
}
