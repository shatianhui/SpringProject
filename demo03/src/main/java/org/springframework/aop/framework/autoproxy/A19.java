package org.springframework.aop.framework.autoproxy;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * description: 动态通知调用 <br>
 * date: 2022/8/1 19:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A19 {
    @Aspect
    static class MyAspect{
        @Before("execution(* foo(..))")  //静态通知调用，不带参数绑定，执行时不需要切点
        public void before1(){
            System.out.println("MyAspect before1");
        }
        @Before("execution(* foo(..))&& args(x)")  //动态通知调用，带参数绑定，执行时需要切点
        public void before2(int x){
            System.out.printf("MyAspect before (%d)\n",x);
        }
    }

    static class Target{
        public void foo(int x){
            System.out.printf("Target foo(%d)\n",x);
        }
    }

    @Configuration
    static class Config{
        @Bean
        public AnnotationAwareAspectJAutoProxyCreator proxyCreator(){
            // 两个作用：①将高级Aspect转化为低级切面Advisor  ②通过ProxyFactory产生代理对象
            return new AnnotationAwareAspectJAutoProxyCreator();
        }

        @Bean
        public MyAspect myAspect(){
            return new MyAspect();
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean(Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class); //解析@Bean注解
        
        context.refresh();
        AnnotationAwareAspectJAutoProxyCreator proxyCreator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> list = proxyCreator.findEligibleAdvisors(Target.class, "target");

        Target target=new Target();
        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisors(list);
        Object proxy = proxyFactory.getProxy(); //获得代理对象

        //获得所有的环绕通知  比如@Before已经转化成了环绕通知
        List<Object> interceptionAdvice = proxyFactory.getInterceptorsAndDynamicInterceptionAdvice(Target.class.getMethod("foo", int.class),Target.class);
        for (Object o : interceptionAdvice) {
            //System.out.println(o);
            showDetail(o);
        }
        /**
         * org.springframework.aop.interceptor.ExposeInvocationInterceptor@3af9c5b7  这是Spring为我们添加的环绕通知。 作用：将methodInvocation放入当前线程（其实还是不是太明白）
         * org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor@37271612
         * org.springframework.aop.framework.InterceptorAndDynamicMethodMatcher@ed7f8b4  重点关注以下这个
         */
    }
    public static void showDetail(Object o) {
        try {
            Class<?> clazz = Class.forName("org.springframework.aop.framework.InterceptorAndDynamicMethodMatcher");
            if (clazz.isInstance(o)) {
                Field methodMatcher = clazz.getDeclaredField("methodMatcher");
                methodMatcher.setAccessible(true);
                Field methodInterceptor = clazz.getDeclaredField("interceptor");
                methodInterceptor.setAccessible(true);
                System.out.println("环绕通知和切点：" + o);
                System.out.println("\t切点为：" + methodMatcher.get(o));
                System.out.println("\t通知为：" + methodInterceptor.get(o));
            } else {
                System.out.println("普通环绕通知：" + o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
