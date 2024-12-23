package com.fastsoft.validation.common.violation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@Slf4j
@Aspect
@Component
public class ValidationAspect {

  @Around("@annotation(annotation)")
  public Object aroundAdvice(
      ProceedingJoinPoint joinPoint, ViolationsExceptionAnnotation annotation) throws Throwable {
    log.debug("Before method: {}", joinPoint.getSignature().getName());

    Object result;

    try {
      result = joinPoint.proceed();
    } catch (VialationsException e) {

      // getMethodParameter
      final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      final Method method = signature.getMethod();
      final int parameterIndex = annotation.parameterIndex();
      final MethodParameter methodParameter = new MethodParameter(method, parameterIndex);

      // getBindingResult
      Object[] args = joinPoint.getArgs();
      final Object target = args[parameterIndex];
      BindingResult bindingResult = null;

      // Find BindingResult in the method arguments
      for (Object arg : args) {
        if (arg instanceof BindingResult) {
          bindingResult = (BindingResult) arg;
          break;
        }
      }
      if (bindingResult == null) {
        bindingResult = new BeanPropertyBindingResult(target, joinPoint.getSignature().getName());
      }

      // factory
      var clazz = annotation.factory();
      Constructor<?> constructor = clazz.getConstructor();
      Object instance = constructor.newInstance();
      MethodArgumentNotValidExceptionFactory factory =
          (MethodArgumentNotValidExceptionFactory) instance;

      factory.setBindingResult(bindingResult);
      factory.setMethodParameter(methodParameter);
      factory.setViolations(e.getViolations());

      throw factory.create(target);
    } catch (Throwable throwable) {
      log.error("Exception in method: {}", joinPoint.getSignature().getName(), throwable);
      throw throwable;
    }

    log.debug("After method: {}", joinPoint.getSignature().getName());
    return result;
  }
}
