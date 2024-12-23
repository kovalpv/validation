package com.fastsoft.validation.infrastructure.adapter.input.rest.data.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateOrderRequest(
    @Valid @Size(min = 1, message = "The array must contain at least one element.")
        List<CreateOrderItemRequest> orderItems) {}
