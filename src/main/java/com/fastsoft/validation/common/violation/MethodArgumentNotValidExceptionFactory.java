package com.fastsoft.validation.common.violation;

import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface MethodArgumentNotValidExceptionFactory<T> {

  void setMethodParameter(MethodParameter methodParameter);

  void setBindingResult(BindingResult bindingResult);

  void setViolations(List<Violation> violations);

  MethodArgumentNotValidException create(T object) throws MethodArgumentNotValidException;
}
