package com.fastsoft.validation.infrastructure.adapter.input.rest;

import com.fastsoft.validation.application.port.in.CreateOrderUseCase;
import com.fastsoft.validation.application.port.in.FindAllOrderIdsUseCase;
import com.fastsoft.validation.application.port.in.GetOrderUseCase;
import com.fastsoft.validation.common.violation.VialationsException;
import com.fastsoft.validation.common.violation.ViolationsExceptionAnnotation;
import com.fastsoft.validation.domain.order.Order;
import com.fastsoft.validation.domain.order.OrderId;
import com.fastsoft.validation.domain.order.OrderItem;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderRequest;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.response.CreateOrderResponse;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.response.OrderResponse;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.response.OrdersResponse;
import com.fastsoft.validation.infrastructure.adapter.input.rest.mapper.OrderRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestAdapter {

  private final CreateOrderUseCase createOrderUseCase;
  private final FindAllOrderIdsUseCase findAllOrderIdsUseCase;
  private final GetOrderUseCase getOrderUseCase;
  private final OrderRestMapper orderRestMapper;

  @PostMapping
  @ViolationsExceptionAnnotation(factory = OrderMethodArgumentNotValidExceptionFactory.class)
  public ResponseEntity<CreateOrderResponse> addOrder(
      @Valid @RequestBody CreateOrderRequest request) throws VialationsException {
    log.info("add new order");

    final List<OrderItem> orderItems = orderRestMapper.toOrderItems(request);
    OrderId orderId = createOrderUseCase.createOrder(orderItems);
    return ResponseEntity.ok(new CreateOrderResponse(orderId));
  }

  @GetMapping
  public ResponseEntity<OrdersResponse> getAllOrders() {
    log.info("get all orders");
    List<OrderId> orderIds = findAllOrderIdsUseCase.findAllIds();
    return ResponseEntity.ok(new OrdersResponse(orderIds));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable OrderId orderId) {
    Order order = getOrderUseCase.getById(orderId);
    return ResponseEntity.ok(new OrderResponse(order));
  }
}
