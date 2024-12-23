package com.fastsoft.validation.infrastructure.adapter.input.rest;

import com.fastsoft.validation.common.violation.MethodArgumentNotValidExceptionFactory;
import com.fastsoft.validation.common.violation.Violation;
import com.fastsoft.validation.domain.order.violation.NotEnoughInventoryViolation;
import com.fastsoft.validation.domain.order.violation.OrderViolation;
import com.fastsoft.validation.domain.order.violation.PriceLessThanViolation;
import com.fastsoft.validation.domain.order.violation.QuantityLessThanViolation;
import com.fastsoft.validation.domain.product.ProductId;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderItemRequest;
import com.fastsoft.validation.infrastructure.adapter.input.rest.data.request.CreateOrderRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@NoArgsConstructor
public class OrderMethodArgumentNotValidExceptionFactory implements
    MethodArgumentNotValidExceptionFactory<CreateOrderRequest> {

  @Setter
  private MethodParameter methodParameter;

  @Setter
  private BindingResult bindingResult;


  private List<OrderViolation> violations;

  @Override
  public void setViolations(List<Violation> violations) {
    this.violations = (List<OrderViolation>) (List<?>) violations;
  }

  @Override
  public MethodArgumentNotValidException create(CreateOrderRequest request)
      throws MethodArgumentNotValidException {
    validate(request);
    throw new MethodArgumentNotValidException(methodParameter, bindingResult);
  }

  private void validate(CreateOrderRequest request) {
    final Map<ProductId, List<OrderViolation>> errors = violations.stream()
        .collect(Collectors.groupingBy(OrderViolation::getProductId));

    for (CreateOrderItemRequest orderItem : request.orderItems()) {
      ProductId productId = orderItem.product().getId();
      int orderIndex = request.orderItems().indexOf(orderItem); // Get the current index

      errors.getOrDefault(productId, List.of())
          .forEach(error -> handleError(error, orderIndex));
    }
  }

  private void handleError(OrderViolation error, int orderIndex) {
    String errorKey = "orderItems[" + orderIndex + "]";

    switch (error) {
      case NotEnoughInventoryViolation violation -> bindingResult.rejectValue(errorKey + ".product",
          violation.getProductId().getIdAsString(), violation.getMessage());
      case PriceLessThanViolation violation -> bindingResult.rejectValue(errorKey + ".price",
          violation.getProductId().getIdAsString(), violation.getMessage());
      case QuantityLessThanViolation violation -> bindingResult.rejectValue(errorKey + ".quantity",
          violation.getProductId().getIdAsString(), violation.getMessage());
      default -> throw new IllegalStateException("Unexpected value: " + error);
    }
  }

}
