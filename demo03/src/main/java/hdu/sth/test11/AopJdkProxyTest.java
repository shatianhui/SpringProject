package hdu.sth.test11;

import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * description: AopJdkProxyTest <br>
 * date: 2022/7/28 15:15 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class AopJdkProxyTest {
    public static void main(String[] args) throws IOException {
        Target target=new Target();
        Foo proxy = (Foo) Proxy.newProxyInstance(AopJdkProxyTest.class.getClassLoader(),
                new Class[]{Foo.class}, (proxy1, method, args1) -> {
                    System.out.println("before");
                    Object obj = method.invoke(target, args1);
                    System.out.println("after");
                    return obj;
                });
        System.out.println(proxy.getClass());
        proxy.foo();
        System.in.read();
    }
}
