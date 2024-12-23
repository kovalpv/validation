package com.fastsoft.validation.infrastructure.out.order;

import com.fastsoft.validation.domain.order.Order;
import com.fastsoft.validation.domain.order.OrderId;
import com.fastsoft.validation.domain.order.OrderNotFoundException;
import com.fastsoft.validation.domain.order.OrderRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private final Set<Order> orders = new HashSet<>();

  @Override
  public void save(Order order) {
    orders.add(order);
  }

  @Override
  public Order getById(OrderId orderId) {
    return orders.stream()
        .filter(order -> order.getId().equals(orderId))
        .findFirst()
        .orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  @Override
  public List<OrderId> findAllIds() {

    return orders.stream().map(Order::getId).collect(Collectors.toList());
  }
}
