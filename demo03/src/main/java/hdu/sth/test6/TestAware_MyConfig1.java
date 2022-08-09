package hdu.sth.test6;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestAware_MyConfig1 <br>
 * date: 2022/8/6 21:41 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestAware_MyConfig1 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("myConfig1",MyConfig1.class);

        // 解析 @Autowired 注解的Bean后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 解析 @PostConstruct 注解的Bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 添加 解析@ComponentScan、@Bean、@Import、@ImportResource注解的后处理器
        context.registerBean(ConfigurationClassPostProcessor.class);

        // 1. 添加beanfactory后处理器；2. 添加bean后处理器；3. 初始化单例。
        context.refresh();

        context.close();
        // 结果：加了@Autowired @PostConstruct方法没有被执行
    }
}
