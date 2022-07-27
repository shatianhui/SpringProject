package hdu.sth.test6;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestAwareAndInitalizingBean <br>
 * date: 2022/7/27 9:41 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestAwareAndInitializingBean {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        // context.registerBean("myBean", MyBean.class);
        //context.registerBean("myConfig1",MyConfig1.class);
        context.registerBean("myConfig2",MyConfig2.class);
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 解析@ComponentScan、@Bean、@Import、@ImportResource注解的后处理器
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();
        context.close();
        /**
         * 结果加了@Autowired和 @PostConstruct注解的方法并没有被执行，而Aware和InitializingBean接口方法都被执行了。
         * 要想@Autowired和@PostConstruct注解生效，需要添加对应的bean后处理器
         */
    }
}
