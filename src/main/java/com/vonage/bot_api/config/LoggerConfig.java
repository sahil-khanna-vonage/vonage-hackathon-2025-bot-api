package com.vonage.bot_api.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggerConfig {

  private static final Logger logger = LoggerFactory.getLogger(LoggerConfig.class);

  @Before("execution(* com.vonage.bot_api..*(..))")
  public void logBefore(JoinPoint joinPoint) {
    logger.info("Entering: {} with arguments: {}",
        joinPoint.getSignature().toShortString(),
        Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(value = "execution(* com.vonage.bot_api..*(..))", returning = "result")
  public void logAfter(JoinPoint joinPoint, Object result) {
    logger.info("Exiting: {} with return value: {}",
        joinPoint.getSignature().toShortString(),
        result);
  }
}
