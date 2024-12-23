package com.fastsoft.validation.domain.order;

import com.fastsoft.validation.domain.product.ProductId;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Order {

  private final OrderId id;
  private final OrderStatus status;
  private final List<OrderItem> orderItems;
  private BigDecimal price;

  public Order(OrderId id, List<OrderItem> orderItems) {
    this.id = id;
    this.orderItems = orderItems;
    this.status = OrderStatus.CREATED;
    calculatePrice();
  }

  public void setOrderItemPrice(ProductId productId, BigDecimal price) {
    OrderItem orderItem =
        orderItems.stream()
            .filter(item -> productId.equals(item.getProductId()))
            .findFirst()
            .orElseThrow(() -> new OrderItemNotFoundException(productId));
    orderItem.setPrice(price);
    calculatePrice();
  }

  private void calculatePrice() {
    this.price =
        this.orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
