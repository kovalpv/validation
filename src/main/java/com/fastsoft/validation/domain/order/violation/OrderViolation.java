package com.fastsoft.validation.domain.order.violation;

import com.fastsoft.validation.common.violation.Violation;
import com.fastsoft.validation.domain.product.ProductId;
import lombok.Getter;

@Getter
public abstract class OrderViolation extends Violation {

  private final ProductId productId;

  public OrderViolation(ProductId productId, String message) {
    super(message);
    this.productId = productId;
  }
}
