package com.fastsoft.validation.domain.order;

import java.util.List;

public interface OrderRepository {

  void save(Order order);

  Order getById(OrderId orderId);

  List<OrderId> findAllIds();
}
