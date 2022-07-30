package hdu.sth.test16;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * description: TestTransactional <br>
 * date: 2022/7/30 17:22 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestTransactional {

    public static void main(String[] args) throws NoSuchMethodException {
        StaticMethodMatcherPointcut pointcut=new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {  //定义匹配规则
                //检查方法上有没有@Transactional注解
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)){
                    return true;
                }
                //查找类(包括父类，实现的接口上都要查找)上是否有@Transactional注解
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)){
                    return true;
                }
                return false;
            }
        };
        System.out.println(pointcut.matches(T1.class.getDeclaredMethod("foo"), T1.class));
        System.out.println(pointcut.matches(T1.class.getDeclaredMethod("bar"), T1.class));
        System.out.println("------------------------");
        System.out.println(pointcut.matches(T2.class.getDeclaredMethod("foo"), T2.class));
        System.out.println(pointcut.matches(T2.class.getDeclaredMethod("bar"), T2.class));
        System.out.println("-------------------------------");
        System.out.println(pointcut.matches(T3.class.getDeclaredMethod("foo"), T3.class));
    }

    static class T1{
        @Transactional
        public void foo(){
        }

        public void bar(){
        }
    }

    @Transactional
    static class T2{
        public void foo(){}
        public void bar(){}
    }

    @Transactional
    interface I1{
        void foo();
    }

    static class T3 implements I1{

        @Override
        public void foo() {
        }
    }
}
