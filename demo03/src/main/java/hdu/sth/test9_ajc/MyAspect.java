package hdu.sth.test9_ajc;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * description: MyAspect <br>
 * date: 2022/7/28 10:32 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Aspect // ⬅️注意此切面并未被Spring 管理
@Slf4j
public class MyAspect {
    @Before("execution(* hdu.sth.test9_ajc..*.*(..))")
    public void before() {
        System.out.println("----");
        log.debug("before()");
    }
}
