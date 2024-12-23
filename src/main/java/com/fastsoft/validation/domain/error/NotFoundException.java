package com.fastsoft.validation.domain.error;

public class NotFoundException extends DomainException {

  public NotFoundException() {}

  public NotFoundException(String message) {
    super(message);
  }
}
