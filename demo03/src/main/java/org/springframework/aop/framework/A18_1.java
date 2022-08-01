package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 模拟调用链过程 <br>
 * date: 2022/8/1 16:49 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A18_1 {
    static class Target{
        public void foo(){
            System.out.println("Target foo...");
        }
    }
    static class Advice1 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice1 before");
            Object res = invocation.proceed();// 调用通知，如果没有通知了，调用目标
            System.out.println("Advice1 after");
            return res;
        }
    }

    static class Advice2 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Advice2 before");
            Object res = invocation.proceed();// 调用通知，如果没有通知了，调用目标
            System.out.println("Advice2 after");
            return res;
        }
    }

    static class MyInvocation implements MethodInvocation{
        private Object target; //目标对象
        private Method method; //目标对象的方法
        private Object[] args; //目标对象的参数
        private List<MethodInterceptor> methodInterceptorList;// 环绕通知集合
        private Integer count=1;//调用次数

        public MyInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> methodInterceptorList) {
            this.target = target;
            this.method = method;
            this.args = args;
            this.methodInterceptorList = methodInterceptorList;
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object[] getArguments() {
            return args;
        }

        @Override
        public Object proceed() throws Throwable {
            if (count>methodInterceptorList.size()){ //调用目标并返回
                Object res = method.invoke(target, args);
                return res;
            }else{  //调用对应的环绕通知，并且count++
                MethodInterceptor interceptor = methodInterceptorList.get(count - 1);
                count++;
                return interceptor.invoke(this);
            }
        }

        @Override
        public Object getThis() {
            return target;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return method;
        }
    }

    public static void main(String[] args) throws Throwable {
        Target target=new Target();
        Advice1 advice1=new Advice1();
        Advice2 advice2=new Advice2();
        List<MethodInterceptor> list=new ArrayList<>();
        list.add(advice1);
        list.add(advice2);
        MyInvocation invocation = new MyInvocation(target, Target.class.getDeclaredMethod("foo"), new Object[0], list);
        invocation.proceed();
    }
}
