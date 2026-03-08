package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity {

  @Id private String sku;
  private String name;
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
