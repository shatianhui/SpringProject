package hdu.sth.test45;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    // 对所有方法增强
    @Before("execution(* hdu.sth.test45.Bean1.*(..))")
    public void before() {
        System.out.println("before");
    }
}
