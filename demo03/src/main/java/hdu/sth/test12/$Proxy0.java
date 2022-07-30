package hdu.sth.test12;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * description: $Proxy0 <br>
 * date: 2022/7/28 16:32 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

public class $Proxy0 extends Proxy implements A12.Foo{
    private static Method foo;
    private static Method bar;
    static {
        try {
            foo=A12.Foo.class.getDeclaredMethod("foo");//静态代码块的异常必须处理
            bar=A12.Foo.class.getDeclaredMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }

    }

    public $Proxy0(InvocationHandler h){
        super(h);
    }

    @Override
    public void foo() {
        try {
            h.invoke(this,foo,new Object[0]);
        }  catch (RuntimeException | Error e) { //对于运行时异常或者Error直接抛出
            throw e;
        } catch (Throwable e) { //对于编译时异常，转化后抛出
            throw new UndeclaredThrowableException(e);
        }

    }

    @Override
    public int bar() {
        try {
            Object res= h.invoke(this,bar,new Object[0]);
            return (int)res;
        } catch (RuntimeException | Error e) { //对于运行时异常或者Error直接抛出
            throw e;
        } catch (Throwable e) { //对于编译时异常，转化后抛出
            throw new UndeclaredThrowableException(e);
        }
    }
}

