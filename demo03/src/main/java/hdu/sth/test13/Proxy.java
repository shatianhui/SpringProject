package hdu.sth.test13;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * description: Proxy <br>
 * 模拟cglib代理编写代理类
 * date: 2022/7/29 11:22 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Proxy extends Target{
    private MethodInterceptor methodInterceptor;

    static Method save0;
    static Method save1;
    static Method save2;

    static MethodProxy save0Proxy;
    static MethodProxy save1Proxy;
    static MethodProxy save2Proxy;

    static {
        try {
            save0=Target.class.getDeclaredMethod("save");
            save1 = Target.class.getDeclaredMethod("save", int.class);
            save2 = Target.class.getDeclaredMethod("save", long.class);
            // 5个参数，目标类，代理类，方法参数的返回值描述，增强方法名，原始方法名  ()表示无参 V表示返回值为void
            save0Proxy=MethodProxy.create(Target.class,Proxy.class,"()V","save","superSave");
            save1Proxy=MethodProxy.create(Target.class,Proxy.class,"(I)V","save","superSave");
            save2Proxy=MethodProxy.create(Target.class,Proxy.class,"(J)V","save","superSave");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    //原始方法
    public void superSave(){
        super.save();
    }

    public void superSave(int i){
        super.save(i);
    }

    public void superSave(long j){
        super.save(j);
    }

    // 带增强功能的方法
    @Override
    public void save() {
        try {
            methodInterceptor.intercept(this,save0,new Object[0],save0Proxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public void save(int i) {
        try {
            methodInterceptor.intercept(this,save1,new Object[]{i},save1Proxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }


    @Override
    public void save(long j) {
        try {
            methodInterceptor.intercept(this,save2,new Object[]{j},save2Proxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
