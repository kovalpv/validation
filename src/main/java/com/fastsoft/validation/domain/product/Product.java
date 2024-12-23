package com.fastsoft.validation.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Product {

  private final ProductId id;
  private final String name;

  @JsonCreator
  public Product(@JsonProperty("id") final ProductId id, @JsonProperty("name") final String name) {
    this.id = id;
    this.name = name;
  }
}
