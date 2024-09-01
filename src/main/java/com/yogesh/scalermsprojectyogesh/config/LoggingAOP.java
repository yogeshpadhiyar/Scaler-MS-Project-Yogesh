package com.yogesh.scalermsprojectyogesh.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAOP {

    @Before(value = "execution(* com.yogesh.scalermsprojectyogesh.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        if(log.isDebugEnabled()) {
            log.debug("Entering Method :: {}.{} with following args {}", className, methodName, Arrays.toString(args));
        }
    }

    @After(value = "execution(* com.yogesh.scalermsprojectyogesh.*.*.*(..))")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        if(log.isDebugEnabled()) {
            log.debug("Exit Method :: {}.{}", className, methodName);
        }
    }

    @AfterReturning(value = "execution(* com.yogesh.scalermsprojectyogesh.*.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        if(log.isDebugEnabled()) {
            log.debug("Exit Method :: {}.{} with following result {}", className, methodName, result);
        }
    }

    @AfterThrowing(value = "execution(* com.yogesh.scalermsprojectyogesh.*.*.*(..))", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        if(log.isDebugEnabled()) {
            log.error("Exit Method :: {}.{} with following exception {}", className, methodName, exception.getMessage());
        }
    }
}
