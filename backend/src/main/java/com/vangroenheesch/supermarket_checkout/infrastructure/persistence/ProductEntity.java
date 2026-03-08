package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
class ProductEntity {

  @Id private String sku;
  private String name;

  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  protected ProductEntity() {}

  public ProductEntity(String sku, String name, BigDecimal price) {
    this.sku = sku;
    this.name = name;
    this.price = price;
  }

  Product toDomain() {
    return new Product(sku, name, price);
  }

  static ProductEntity fromDomain(Product product) {
    return new ProductEntity(product.sku(), product.name(), product.price());
  }
}
