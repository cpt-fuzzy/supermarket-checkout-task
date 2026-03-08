package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Offer;
import com.vangroenheesch.supermarket_checkout.domain.port.OfferRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
class JpaOfferRepositoryAdapter implements OfferRepositoryPort {

  private final JpaOfferRepository repository;

  JpaOfferRepositoryAdapter(JpaOfferRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Offer> findById(String id) {
    return repository.findById(id).map(OfferEntity::toDomain);
  }

  @Override
  public Optional<Offer> findByProductSku(String productSku) {
    return repository.findByProductSku(productSku).map(OfferEntity::toDomain);
  }

  @Override
  public List<Offer> findAll() {
    return repository.findAll().stream().map(OfferEntity::toDomain).toList();
  }
}
