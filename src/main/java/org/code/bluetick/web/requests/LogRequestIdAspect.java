package org.code.bluetick.web.requests;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class LogRequestIdAspect {

    private static final String LOG_REQUEST_ID = "request-id";

    @Around("@annotation(LogRequestId)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        try(MDC.MDCCloseable requestIdMDC = MDC.putCloseable(LOG_REQUEST_ID, UUID.randomUUID().toString())) {
            return joinPoint.proceed();
        }
    }
}
