package com.vangroenheesch.supermarket_checkout.domain.port;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.model.Product;

public interface ProductRepositoryPort {
  /**
   * Finds a product by id
   *
   * @param id identifier of the product
   * @return the product, or empty if not found
   */
  Optional<Product> findById(String id);

  /**
   * Finds all products
   *
   * @return all products
   */
  List<Product> findAll();
}
