package com.fastsoft.validation.infrastructure.adapter.input.rest.data.response;

import com.fastsoft.validation.domain.order.Order;
import lombok.Getter;

@Getter
public class OrderResponse {

  private final Order order;

  public OrderResponse(Order order) {
    this.order = order;
  }
}
