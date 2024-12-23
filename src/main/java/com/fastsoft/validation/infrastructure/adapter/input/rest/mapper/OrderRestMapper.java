package com.fastsoft.validation.infrastructure.adapter.input.rest.mapper;

import com.fastsoft.validation.domain.order.OrderItem;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderItemRequest;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderRequest;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderRestMapper {

  default List<OrderItem> toOrderItems(CreateOrderRequest createOrderRequest) {
    return toOrderItems(createOrderRequest.orderItems());
  }

  List<OrderItem> toOrderItems(List<CreateOrderItemRequest> orderItems);
}
