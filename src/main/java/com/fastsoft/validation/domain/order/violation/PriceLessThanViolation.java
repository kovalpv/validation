package com.fastsoft.validation.domain.order.violation;

import com.fastsoft.validation.domain.product.ProductId;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class PriceLessThanViolation extends OrderViolation {

  public PriceLessThanViolation(ProductId productId, BigDecimal totalPrice) {
    super(productId, "Общая цена товара ниже чем %.2f".formatted(totalPrice));
  }
}
