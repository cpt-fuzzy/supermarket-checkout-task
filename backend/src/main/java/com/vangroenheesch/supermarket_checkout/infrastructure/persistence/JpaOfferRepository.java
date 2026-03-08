package com.vangroenheesch.supermarket_checkout.infrastructure.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaOfferRepository extends JpaRepository<OfferEntity, String> {

  Optional<OfferEntity> findByProductSku(String productSku);
}
