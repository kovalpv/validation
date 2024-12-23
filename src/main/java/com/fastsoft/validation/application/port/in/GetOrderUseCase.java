package com.fastsoft.validation.application.port.in;

import com.fastsoft.validation.domain.order.Order;
import com.fastsoft.validation.domain.order.OrderId;

public interface GetOrderUseCase {

  Order getById(OrderId orderId);
}
