package com.fastsoft.validation.common.violation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Violation {

  private final String message;
}
