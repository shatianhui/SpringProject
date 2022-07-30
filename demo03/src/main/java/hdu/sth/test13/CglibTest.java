package hdu.sth.test13;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * description: CglibTest <br>
 * date: 2022/7/29 11:27 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class CglibTest {
    public static void main(String[] args) {
        Proxy proxy=new Proxy();
        Target target = new Target();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object p, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                // 增强代码
                System.out.println("before.....");
                //return method.invoke(target,objects);
                //return methodProxy.invoke(target,objects);  //内部未使用反射  结合目标
                return methodProxy.invokeSuper(p,objects);  //内部未使用反射  结合代理
            }
        });
        proxy.save();
        proxy.save(1);
        proxy.save(2L);
    }
}
