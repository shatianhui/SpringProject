package hdu.sth.test43;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * description: A43 <br>
 * date: 2022/8/18 17:03 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@ComponentScan("hdu.sth.test43")
public class A43 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(A43.class);
        Bean1 bean1 = (Bean1) context.getBean("bean1");//按照工厂名字取，取出来的结果是产品对象
        Bean1 bean2 = (Bean1) context.getBean("bean1");//按照工厂名字取，取出来的结果是产品对象
        Bean1 bean3 = (Bean1) context.getBean("bean1");//按照工厂名字取，取出来的结果是产品对象

        // 演示单例
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);

        // 获取工厂本身
        System.out.println(context.getBean(Bean1FactoryBean.class));
        System.out.println(context.getBean("&bean1"));
        context.close();
    }
}
