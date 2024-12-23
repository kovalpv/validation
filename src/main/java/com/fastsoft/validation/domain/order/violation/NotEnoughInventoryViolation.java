package com.fastsoft.validation.domain.order.violation;

import com.fastsoft.validation.domain.product.ProductId;
import lombok.Getter;

@Getter
public class NotEnoughInventoryViolation extends OrderViolation {

  public NotEnoughInventoryViolation(ProductId productId) {
    super(productId, "Не достаточно товара на складе");
  }
}
