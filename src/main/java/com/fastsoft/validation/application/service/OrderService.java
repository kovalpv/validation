package com.fastsoft.validation.application.service;

import com.fastsoft.validation.application.port.in.CreateOrderUseCase;
import com.fastsoft.validation.application.port.in.FindAllOrderIdsUseCase;
import com.fastsoft.validation.application.port.in.GetOrderUseCase;
import com.fastsoft.validation.common.violation.VialationsException;
import com.fastsoft.validation.common.violation.Violation;
import com.fastsoft.validation.domain.order.Order;
import com.fastsoft.validation.domain.order.OrderId;
import com.fastsoft.validation.domain.order.OrderItem;
import com.fastsoft.validation.domain.order.OrderRepository;
import com.fastsoft.validation.domain.order.violation.NotEnoughInventoryViolation;
import com.fastsoft.validation.domain.order.violation.PriceLessThanViolation;
import com.fastsoft.validation.domain.order.violation.QuantityLessThanViolation;
import com.fastsoft.validation.domain.product.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OrderService implements CreateOrderUseCase, GetOrderUseCase, FindAllOrderIdsUseCase {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Override
  public OrderId createOrder(List<OrderItem> orderItems) throws VialationsException {
    final OrderId orderId = new OrderId(UUID.randomUUID());
    final Order order = new Order(orderId, orderItems);

    List<Violation> violations = new LinkedList<>();
    updateOrder(order);
    order
        .getOrderItems()
        .forEach(
            orderItem -> {
              if (!productRepository.isAvailable(
                  orderItem.getProductId(), orderItem.getQuantity())) {
                violations.add(new NotEnoughInventoryViolation(orderItem.getProductId()));
              }

              BigDecimal minimalPrice = new BigDecimal(80);
              minimalPrice = minimalPrice.setScale(2, RoundingMode.HALF_UP);

              if (minimalPrice.compareTo(orderItem.getTotalPrice()) > 0) {
                violations.add(new PriceLessThanViolation(orderItem.getProductId(), minimalPrice));
              }

              var quantity = 2;
              if (orderItem.getQuantity() < quantity) {
                violations.add(new QuantityLessThanViolation(orderItem.getProductId(), quantity));
              }
            });

    if (!violations.isEmpty()) {
      throw new VialationsException(violations);
    }

    orderRepository.save(order);
    return order.getId();
  }

  @Override
  public Order getById(OrderId orderId) {
    return orderRepository.getById(orderId);
  }

  @Override
  public List<OrderId> findAllIds() {
    return orderRepository.findAllIds();
  }

  private void updateOrder(Order order) {
    order
        .getOrderItems()
        .forEach(
            orderItem -> {
              var productId = orderItem.getProductId();
              BigDecimal price = productRepository.getPriceById(productId);
              order.setOrderItemPrice(productId, price);
            });
  }
}
