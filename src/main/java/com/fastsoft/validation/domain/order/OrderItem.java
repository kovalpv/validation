package com.fastsoft.validation.domain.order;

import com.fastsoft.validation.domain.product.Product;
import com.fastsoft.validation.domain.product.ProductId;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;

@Getter
public class OrderItem {

  private final ProductId productId;
  private BigDecimal price;
  private final Integer quantity;
  private BigDecimal totalPrice;

  public OrderItem(
      final Product product,
      final BigDecimal price,
      final Integer quantity) {
    this.productId = product.getId();
    this.price = price;
    this.quantity = quantity;
    calculateTotalPrice();
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
    calculateTotalPrice();
  }

  private void calculateTotalPrice() {
    this.totalPrice = this.price.multiply(BigDecimal.valueOf(this.quantity));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(productId, orderItem.productId)
        && Objects.equals(price, orderItem.price)
        && Objects.equals(quantity, orderItem.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, price);
  }
}
