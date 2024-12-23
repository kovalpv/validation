package com.fastsoft.validation.infrastructure.adapter.input.rest.data.response;

import com.fastsoft.validation.domain.order.OrderId;
import java.util.List;

public record OrdersResponse(List<OrderId> ids) {}
