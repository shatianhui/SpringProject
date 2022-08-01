package org.springframework.aop.framework.autoproxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectInstanceFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.aop.aspectj.annotation.AspectJAdvisorFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * description: A17_2 <br>
 * date: 2022/7/31 21:13 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A17_2 {
    public static void main(String[] args) {
        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());
        List<Advisor> advisors = new ArrayList<>();
        // 将高级切面转化成低级切面
        for (Method method : Aspect.class.getDeclaredMethods()) { //遍历切面类的所有方法
            //依次解析@Before @After @Around等五个注解，以解析@Before为例
            if (method.isAnnotationPresent(Before.class)){
                String expression = method.getAnnotation(Before.class).value();//获得切点表达式
                //创建切点
                AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //方法转化成通知类
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
                //切面
                Advisor advisor=new DefaultPointcutAdvisor(pointcut,advice);
                advisors.add(advisor);
            }
        }
        for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }

    }

    static class Aspect {
        @Before("execution(* foo())")
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* foo())")
        public void before2() {
            System.out.println("before2");
        }

        public void after() {
            System.out.println("after");
        }

        public void afterReturning() {
            System.out.println("afterReturning");
        }

        public void afterThrowing() {
            System.out.println("afterThrowing");
        }

        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            try {
                System.out.println("around...before");
                return pjp.proceed();
            } finally {
                System.out.println("around...after");
            }
        }
    }

    static class Target {
        public void foo() {
            System.out.println("target foo");
        }
    }
}
