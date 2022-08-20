package hdu.sth.test45;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.Advised;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class A45 {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(A45.class, args);
        //AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(A45.class);
        Bean1 proxy = context.getBean(Bean1.class);
        proxy.m1();

        /*
            1.演示 spring 代理的设计特点
                依赖注入和初始化影响的是原始对象
                代理与目标是两个对象，二者成员变量并不共用数据
         */
//        showProxyAndTarget(proxy);
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>");
//        System.out.println(proxy.getBean2());
//        System.out.println(proxy.isInitialized());

        /*
            2.演示 static 方法、final 方法、private 方法均无法增强
         */

//        proxy.m1();
//        proxy.m2();
//        proxy.m3();
//        Method m4 = Bean1.class.getDeclaredMethod("m1");
//        m4.setAccessible(true);
//        m4.invoke(proxy);

        context.close();
    }


    public static void showProxyAndTarget(Bean1 proxy) throws Exception {
//        System.out.println(">>>>> 代理中的成员变量");
//        System.out.println("\tinitialized=" + proxy.initialized);
//        System.out.println("\tbean2=" + proxy.bean2);
//
//        if (proxy instanceof Advised advised) {
//            System.out.println(">>>>> 目标中的成员变量");
//            Bean1 target = (Bean1) advised.getTargetSource().getTarget();
//            System.out.println("\tinitialized=" + target.initialized);
//            System.out.println("\tbean2=" + target.bean2);
//        }
    }

}
