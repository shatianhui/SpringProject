package org.springframework.aop.framework.autoproxy;
// 主要是为了调用AnnotationAwareAspectJAutoProxyCreator中的protected方法
import hdu.sth.test13.Target;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * description: A17 <br>
 * date: 2022/7/31 18:46 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A17 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("config",Config.class);
        context.registerBean("aspect1",Aspect1.class);
        context.registerBean(ConfigurationClassPostProcessor.class);//加入bean后处理器，解析@Bean注解

        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class); //两个作用

        context.refresh();
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }

        AnnotationAwareAspectJAutoProxyCreator creator=context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        // 找到所有有资格[跟目标类的方法可以匹配的上]的低级切面Advisor[高级切面转化为低级切面]
        List<Advisor> advisors = creator.findEligibleAdvisors(Target1.class, "target1");
//        for (Advisor advisor : advisors) {
//            System.out.println(advisor);
//        }
        /**
         * 一共四个  一个是低级且切面 两个是高级切面转化成低级切面
         * org.springframework.aop.interceptor.ExposeInvocationInterceptor.ADVISOR
         * org.springframework.aop.support.DefaultPointcutAdvisor: pointcut [AspectJExpressionPointcut: () execution(* foo())]; advice [org.springframework.aop.framework.autoproxy.A17$Config$1@158da8e]
         * InstantiationModelAwarePointcutAdvisor: expression [execution(* foo())]; advice method [public void org.springframework.aop.framework.autoproxy.A17$Aspect1.before()]; perClauseKind=SINGLETON
         * InstantiationModelAwarePointcutAdvisor: expression [execution(* foo())]; advice method [public void org.springframework.aop.framework.autoproxy.A17$Aspect1.after()]; perClauseKind=SINGLETON
         */
        Object o1 = creator.wrapIfNecessary(new Target1(), "target1", "target1");
        System.out.println(o1.getClass());
        Object o2 = creator.wrapIfNecessary(new Target2(), "target2", "target2");
        System.out.println(o2);

        ((Target1) o1).foo();
    }

    static class Target1{
        public void foo(){
            System.out.println("Target1 foo()...");
        }
    }

    static class Target2{
        public void bar(){
            System.out.println("Target2 bar()...");
        }
    }

    @Aspect
    @Order(1)   //设置切面的优先级，数字越小，先被执行
    static class Aspect1{   //定义一个高级切面类
        @Before("execution(* foo())")
        public void before(){
            System.out.println("Aspect1 before...");
        }

        @After("execution(* foo())")
        // @Order 加在方法上，也是没有什么用的
        public void after(){
            System.out.println("Aspect1 after...");
        }
    }

    static class Config{  //在配置类中定义一个低级切面Advisor
        @Bean
        // 使用@Order是没有作用的   对于低级切面只能通过set方式
        public DefaultPointcutAdvisor advisor(MethodInterceptor advice3){
            AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor(pointcut,advice3);
            advisor.setOrder(2);
            return advisor;
        }

        @Bean
        public MethodInterceptor advice3(){
            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {//循环通知
                    System.out.println("advice3 before......");
                    Object result = invocation.proceed();
                    System.out.println("advice3 after.......");
                    return result;
                }
            };
        }
    }
}
