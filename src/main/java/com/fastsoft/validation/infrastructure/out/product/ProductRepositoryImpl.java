package com.fastsoft.validation.infrastructure.out.product;

import com.fastsoft.validation.domain.product.ProductId;
import com.fastsoft.validation.domain.product.ProductPriceNotFoundException;
import com.fastsoft.validation.domain.product.ProductRepository;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final Map<ProductId, Integer> productIdMap =
      Map.of(
          new ProductId("682c3a17-e4fe-4dee-baab-e3e707326797"), 2,
          new ProductId("682c3a17-e4fe-4dee-baab-e3e707326791"), 2);

  private final Map<ProductId, BigDecimal> productIdPriceMap =
      Map.of(
          new ProductId("682c3a17-e4fe-4dee-baab-e3e707326797"), BigDecimal.valueOf(79.95),
          new ProductId("682c3a17-e4fe-4dee-baab-e3e707326791"), BigDecimal.valueOf(79.99));

  @Override
  public boolean isAvailable(ProductId productId, int quantity) {
    return productIdMap.getOrDefault(productId, 0) >= quantity;
  }

  @Override
  public BigDecimal getPriceById(ProductId productId) {
    if (!productIdPriceMap.containsKey(productId)) {
      throw new ProductPriceNotFoundException(productId);
    }
    return productIdPriceMap.get(productId);
  }
}
