package com.vangroenheesch.supermarket_checkout.domain.exception;

/** Throw when a cart references a product SKU that does not exist */
public class ProductNotFoundException extends RuntimeException {
  private final String productSku;

  /**
   * @param productSku the SKU that was not found
   */
  public ProductNotFoundException(String productSku) {
    super("Product not found: " + productSku);
    this.productSku = productSku;
  }

  /**
   * Returns the SKU of the product that was not found
   *
   * @return missing product SKU
   */
  public String getProductSku() {
    return productSku;
  }
}
