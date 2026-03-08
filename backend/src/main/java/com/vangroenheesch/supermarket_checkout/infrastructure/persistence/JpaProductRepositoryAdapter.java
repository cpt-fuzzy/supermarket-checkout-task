package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import com.vangroenheesch.supermarket_checkout.domain.model.Product;
import com.vangroenheesch.supermarket_checkout.domain.port.ProductRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JpaProductRepositoryAdapter implements ProductRepositoryPort {

  private final JpaProductRepository repository;

  JpaProductRepositoryAdapter(JpaProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Product> findById(String id) {
    return repository.findById(id).map(ProductEntity::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return repository.findAll().stream().map(ProductEntity::toDomain).toList();
  }
}
