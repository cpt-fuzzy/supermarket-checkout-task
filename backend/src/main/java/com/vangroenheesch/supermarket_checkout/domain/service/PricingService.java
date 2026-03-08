package com.vangroenheesch.supermarket_checkout.domain.service;

import module java.base;

import com.vangroenheesch.supermarket_checkout.domain.exception.DomainValidationException;
import com.vangroenheesch.supermarket_checkout.domain.exception.ProductNotFoundException;
import com.vangroenheesch.supermarket_checkout.domain.model.*;

// TODO: Think about refactoring as a @Service component
public class PricingService {

  /**
   * Calculates a receipt for a given cart and applies any matching offers
   *
   * @param cart shopping cart; must not be empty
   * @param productsBySku products by SKU; must container every SKU in the cart
   * @param offersByProductSku active offers by SKU; may be empty
   * @return the calculated receipt with line items, total and savings
   */
  public Receipt calculateReceipt(
      Cart cart, Map<String, Product> productsBySku, Map<String, Offer> offersByProductSku) {

    Objects.requireNonNull(cart, "Cart must not be null");
    Objects.requireNonNull(productsBySku, "Products map must not be null");
    Objects.requireNonNull(offersByProductSku, "Offers map must not be null");

    if (cart.isEmpty()) {
      throw new DomainValidationException("Cart must not be empty");
    }

    List<ReceiptLine> lines = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;
    BigDecimal saved = BigDecimal.ZERO;

    for (CartItem item : cart.items()) {
      Product product = productsBySku.get(item.productSku());
      if (product == null) {
        throw new ProductNotFoundException(item.productSku());
      }

      Offer offer = offersByProductSku.get(item.productSku());
      ReceiptLine line = calculateLine(product, item.quantity(), offer);

      lines.add(line);
      total = total.add(line.lineTotal());
      saved = saved.add(line.lineSaved());
    }

    return new Receipt(lines, total, saved);
  }

  /**
   * Calculates a receipt line for a single product with applicable offers
   *
   * @param product product being purchased
   * @param quantity number of units
   * @param offer active offer for this product, or {@code null} if none
   * @return the calculated receipt line
   */
  private ReceiptLine calculateLine(Product product, int quantity, Offer offer) {
    BigDecimal unitPrice = product.price();
    BigDecimal fullPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

    if (offer != null
        && offer.type() == Offer.OfferType.N_FOR_PRICE
        && quantity >= offer.requiredQuantity()) {
      int offerApplications = quantity / offer.requiredQuantity();
      int remainder = quantity % offer.requiredQuantity();

      BigDecimal lineTotal =
          offer
              .offerPrice()
              .multiply(BigDecimal.valueOf(offerApplications))
              .add(unitPrice.multiply(BigDecimal.valueOf(remainder)));
      BigDecimal lineSaved = fullPrice.subtract(lineTotal);

      String description = offer.requiredQuantity() + " for " + offer.offerPrice();

      return new ReceiptLine(
          product.name(), quantity, unitPrice, lineTotal, description, lineSaved);
    }

    return new ReceiptLine(product.name(), quantity, unitPrice, fullPrice, null, BigDecimal.ZERO);
  }
}
