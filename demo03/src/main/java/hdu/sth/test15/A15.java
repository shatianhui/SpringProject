package hdu.sth.test15;

import hdu.sth.test13.Target;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * description: A15 <br>
 * date: 2022/7/30 15:57 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A15 {
    public static void main(String[] args) {
        //准备好切点
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())"); //设置表达式
        //准备好通知
        MethodInterceptor advice=new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                //增强代码
                System.out.println("before....");
                //目标方法的调用
                Object res = invocation.proceed();
                System.out.println("after....");
                return res;
            }
        };
        //准备好切面,切面中就含有切点和通知
        DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor(pointcut,advice);
        //创建代理
        Target2 target=new Target2();
        ProxyFactory factory=new ProxyFactory();
        factory.setTarget(target);
        factory.addAdvisor(advisor);
        //factory.setInterfaces(Target1.class.getInterfaces());//设置目标类实现的接口
        factory.setProxyTargetClass(false);
        Target2 proxy = (Target2) factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();

    }

    interface I1{
        void foo();
        void bar();
    }

    static class Target1 implements I1{

        @Override
        public void foo() {
            System.out.println("foo()....");
        }

        @Override
        public void bar() {
            System.out.println("bar()....");
        }
    }
    static class Target2 {

        public void foo() {
            System.out.println("foo()....");
        }

        public void bar() {
            System.out.println("bar()....");
        }
    }
}
