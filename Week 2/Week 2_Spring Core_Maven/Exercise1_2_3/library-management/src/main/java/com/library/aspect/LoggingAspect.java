package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before("execution(public void com.library.service.BookService.manageBooks())")
    public void logStartTime(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        System.out.println("Method " + joinPoint.getSignature() + " started at: " + startTime.get());
    }

    @After("execution(public void com.library.service.BookService.manageBooks())")
    public void logEndTime(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime.get();
        System.out.println("Method " + joinPoint.getSignature() + " ended at: " + endTime);
        System.out.println("Execution time: " + duration + " ms");
        startTime.remove(); // Clean up thread-local variable
    }
}
