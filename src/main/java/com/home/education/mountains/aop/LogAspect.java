package com.home.education.mountains.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogAspect {

	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	@Before("execution(* com.home.education.mountains.service.impl.*.*(..))")
	public void logBeforeWrite(JoinPoint joinPoint) {
		buildMessage(joinPoint);
	}

	@Before("execution(* com.home.education.mountains.dao.impl.*.*(..))")
	public void logBeforeDaoRead(JoinPoint joinPoint) {
		buildMessage(joinPoint);
	}

	@Before("execution(* com.home.education.mountains.controller.*.*(..))")
	public void logBeforeConrollers(JoinPoint joinPoint) {
		buildMessage(joinPoint);
	}
	
	private void buildMessage(JoinPoint joinPoint) {
		StringBuilder builder = new StringBuilder();
		builder.append(joinPoint.getSourceLocation().getWithinType().getSimpleName() + " ");
		builder.append(joinPoint.getSignature().getName() + " ");
		Arrays.stream(joinPoint.getArgs()).forEach(signatureArg -> builder.append(signatureArg));
		log.info(builder.toString());
	}

}
