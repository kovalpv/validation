package com.fastsoft.validation.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ProductId {

  private final UUID id;

  public ProductId(String id) {
    this.id = UUID.fromString(id);
  }

  @JsonValue
  public String getIdAsString() {
    return id.toString();
  }

  @JsonCreator
  public static ProductId fromString(String id) {
    return new ProductId(UUID.fromString(id));
  }
}
