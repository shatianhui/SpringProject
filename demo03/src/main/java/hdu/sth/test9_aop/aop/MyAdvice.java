package hdu.sth.test9_aop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * description: MyAdvice <br>
 * 定义通知类，在该类中定义切入点，定义切面，将该类交由Spring管理，并标识为切面类
 * date: 2022/7/28 9:58 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Component
@Aspect
public class MyAdvice {

    @Around("execution(* hdu.sth.test9_aop..*Service.*(..))")
    public void method(ProceedingJoinPoint pjp) throws Throwable {
        long start=System.currentTimeMillis();
        Object result = pjp.proceed();
        long end=System.currentTimeMillis();
        System.out.println("执行时间"+(end-start)+"ms");
    }
}
