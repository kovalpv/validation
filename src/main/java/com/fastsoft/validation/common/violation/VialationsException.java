package com.fastsoft.validation.common.violation;

import java.util.List;
import lombok.Getter;

@Getter
public class VialationsException extends Exception {

  private final List<Violation> violations;

  public VialationsException(List<Violation> violations) {
    this.violations = violations;
  }
}
