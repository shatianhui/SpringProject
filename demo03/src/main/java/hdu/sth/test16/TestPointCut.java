package hdu.sth.test16;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.transaction.annotation.Transactional;

/**
 * description: TestPointCut <br>
 * date: 2022/7/30 16:53 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestPointCut {
    public static void main(String[] args) throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        //按照方法名进行匹配
        pointcut.setExpression("execution(* bar())");
        System.out.println(pointcut.matches(T1.class.getDeclaredMethod("foo"), T1.class));//传递目标类的方法及目标类
        System.out.println(pointcut.matches(T1.class.getDeclaredMethod("bar"), T1.class));
        System.out.println("------------------");
        AspectJExpressionPointcut pointcut1=new AspectJExpressionPointcut();
        //根据注解匹配
        pointcut1.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
        System.out.println(pointcut1.matches(T1.class.getDeclaredMethod("foo"), T1.class));
        System.out.println(pointcut1.matches(T1.class.getDeclaredMethod("bar"), T1.class));
    }

    static class T1{
        @Transactional
        public void foo(){
        }

        public void bar(){
        }
    }
}
