package com.fastsoft.validation.infrastructure.adapter.input.rest.data.request;

import com.fastsoft.validation.domain.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateOrderItemRequest(
    @NotNull Product product, BigDecimal price, @Min(1) Integer quantity) {}
