package com.fastsoft.validation.domain.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class OrderId {

  private final UUID id;

  public OrderId(String id) {
    this.id = UUID.fromString(id);
  }

  @JsonValue
  public String getIdAsString() {
    return id.toString();
  }

  @JsonCreator
  public static OrderId fromString(String id) {
    return new OrderId(UUID.fromString(id));
  }
}
