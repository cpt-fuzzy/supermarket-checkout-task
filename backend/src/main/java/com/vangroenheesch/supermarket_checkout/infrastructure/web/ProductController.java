package com.vangroenheesch.supermarket_checkout.infrastructure.web;

import module java.base;

import com.vangroenheesch.supermarket_checkout.application.product.GetProductCatalogUseCase;
import com.vangroenheesch.supermarket_checkout.infrastructure.web.dto.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {

  private final GetProductCatalogUseCase getProductCatalog;

  ProductController(GetProductCatalogUseCase getProductCatalog) {
    this.getProductCatalog = getProductCatalog;
  }

  @GetMapping
  List<ProductResponse> getProducts() {
    return getProductCatalog.getProductCatalog().stream().map(ProductResponse::from).toList();
  }
}
