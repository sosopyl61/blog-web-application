package com.rymtsou.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EndpointsAuditAspect {

    private static final Logger log = LoggerFactory.getLogger(EndpointsAuditAspect.class);

    @Pointcut("within(com.rymtsou.controller..*)")
    public void withinController() {}

    @Around("withinController()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName().substring(23);
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        log.info("[START] endpoint {} in {}", methodName, className);

        Object result;
        result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("[END] of {} in {} ({} ms)", methodName, className,  duration);

        return result;
    }
}
