package com.fastsoft.validation.domain.order;

import com.fastsoft.validation.domain.error.NotFoundException;

public class OrderNotFoundException extends NotFoundException {

  public OrderNotFoundException(OrderId orderId) {
    super("Заказ %s не найден: ".formatted(orderId.getId()));
  }
}
