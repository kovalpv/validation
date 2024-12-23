package com.fastsoft.validation.application.port.in;

import com.fastsoft.validation.domain.order.OrderId;
import java.util.List;

public interface FindAllOrderIdsUseCase {

  List<OrderId> findAllIds();
}
