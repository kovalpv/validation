package com.fastsoft.validation.domain.order.violation;

import com.fastsoft.validation.domain.product.ProductId;
import lombok.Getter;

@Getter
public class QuantityLessThanViolation extends OrderViolation {

  public QuantityLessThanViolation(ProductId productId, int quantity) {
    super(productId, "Количество товара ниже чем %d".formatted(quantity));
  }
}
