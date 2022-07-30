package hdu.sth.test11;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * description: AopCglibTest <br>
 * date: 2022/7/28 15:43 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class AopCglibTest {
    public static void main(String[] args) {
        Target target=new Target();

        Target proxy = (Target) Enhancer.create(Target.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object p, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before....");
                // Object res = method.invoke(target, args);// 用方法反射调用目标
                // Object res = methodProxy.invoke(target, args);// 内部没有使用反射，参数需要目标对象  【Spring使用】
                Object res = methodProxy.invokeSuper(p, args); // 内部没有使用反射，参数需要代理对象
                System.out.println("after....");
                return res;
            }
        });
        proxy.foo();
    }
}
