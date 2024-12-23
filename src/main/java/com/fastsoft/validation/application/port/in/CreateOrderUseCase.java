package com.fastsoft.validation.application.port.in;

import com.fastsoft.validation.common.violation.VialationsException;
import com.fastsoft.validation.domain.order.OrderId;
import com.fastsoft.validation.domain.order.OrderItem;
import java.util.List;

public interface CreateOrderUseCase {

  OrderId createOrder(List<OrderItem> orderItems) throws VialationsException;
}
