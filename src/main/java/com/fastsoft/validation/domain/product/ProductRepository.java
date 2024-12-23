package com.fastsoft.validation.domain.product;

import java.math.BigDecimal;

public interface ProductRepository {

  boolean isAvailable(ProductId productId, int quantity);

  BigDecimal getPriceById(ProductId productId);
}
