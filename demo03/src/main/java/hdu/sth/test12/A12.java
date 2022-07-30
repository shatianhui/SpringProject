package hdu.sth.test12;

import hdu.sth.test11.Foo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * description: A12 <br>
 * date: 2022/7/28 16:30 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class A12 {
    interface Foo {
        void foo();
        int bar();
    }
//    interface InvocationHandler{
//        Object invoke(Object proxy,Method method,Object[] args) throws Throwable;
//    }
    static class Target implements Foo {
        public void foo() {
            log.debug("target foo");
        }
        public int bar() {
            log.debug("target bar");
            return 100;
        }
    }

    public static void main(String[] args) {
        Foo proxy =new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy,Method method, Object[] args) throws Throwable {
                //代码增强
                System.out.println("before....");
                //目标对象的方法调用  通过反射
                Object res = method.invoke(new Target(), args);
                return res;
            }
        });
        System.out.println(proxy.bar());
        proxy.foo();
    }
}
