package com.fastsoft.validation.domain.order;

import com.fastsoft.validation.domain.error.NotFoundException;
import com.fastsoft.validation.domain.product.ProductId;

public class OrderItemNotFoundException extends NotFoundException {

  public OrderItemNotFoundException(ProductId productId) {
    super("Не найден позиция в заказе %s".formatted(productId.getId().toString()));
  }
}
