package vn.com.atomi.openbanking.authservice.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class Loggers {
    @Pointcut("@annotation(vn.com.atomi.openbanking.authservice.common.annotations.UseLogging)")
    public void loggableMethod() {
    }
    @Around("loggableMethod()")
    public Object adviceAround(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        Object[] args = jp.getArgs();
        List<String> arrParams = new ArrayList<>();
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            String params = String.format("%s: %s", codeSignature.getParameterNames()[argIndex], args[argIndex]);
            arrParams.add(params);
        }
        String listString = arrParams.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        String func = String.format("%s (%s)", jp.getSignature().getName(), listString);
        long end = System.currentTimeMillis();
        log.info("@Around called | user {} | func {} of {} | return {} | res time {} | start {} | end {}",
                "TODO",func, jp.getSignature().getDeclaringType().getSimpleName(),proceed.toString(),
                end - start, start, end);
        return proceed;
    }

    @AfterThrowing("loggableMethod()")
    public void adviceAfterThrowing(JoinPoint jp) {
        log.info("@AfterThrowing called | func {} {}", jp.getSignature().getName(),
                jp.getSignature().getDeclaringType().getSimpleName());
    }

}