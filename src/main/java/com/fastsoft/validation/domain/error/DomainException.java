package com.fastsoft.validation.domain.error;

public class DomainException extends RuntimeException {

  public DomainException() {}

  public DomainException(String message) {
    super(message);
  }
}
