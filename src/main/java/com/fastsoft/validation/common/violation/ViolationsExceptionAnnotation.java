package com.fastsoft.validation.common.violation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViolationsExceptionAnnotation {

  int parameterIndex() default 0;

  Class<? extends MethodArgumentNotValidExceptionFactory> factory();
}
