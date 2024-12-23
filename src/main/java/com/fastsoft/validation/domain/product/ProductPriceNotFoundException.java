package com.fastsoft.validation.domain.product;

import com.fastsoft.validation.domain.error.NotFoundException;

public class ProductPriceNotFoundException extends NotFoundException {

  public ProductPriceNotFoundException(ProductId productId) {
    super("Цена для продукта %s не найден.".formatted(productId.getId()));
  }
}
