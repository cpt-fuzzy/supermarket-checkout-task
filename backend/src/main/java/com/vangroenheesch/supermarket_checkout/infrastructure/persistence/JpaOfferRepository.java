package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import module java.base;

import org.springframework.data.jpa.repository.JpaRepository;

interface JpaOfferRepository extends JpaRepository<OfferEntity, String> {

  Optional<OfferEntity> findByProductSku(String productSku);
}
