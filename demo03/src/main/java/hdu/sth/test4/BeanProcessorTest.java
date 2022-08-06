package hdu.sth.test4;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: BeanProcessorTest <br>
 * date: 2022/8/5 10:53 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class BeanProcessorTest {
    public static void main(String[] args) {
        // 一个干净的容器
        GenericApplicationContext context=new GenericApplicationContext();
        // 注册bean
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);

        // 设置解析 @Value 注解的解析器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        // 设置解析 @Value 注解的解析器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 添加解析 @Autowired 和 @Value 注解的后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 添加解析 @Resource、@PostConstruct、@PreDestroy 注解的后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 添加解析 @ConfigurationProperties注解的后处理器
        // ConfigurationPropertiesBindingPostProcessor后处理器不能像上面几种后处理器那样用context直接注册上去
        // 需要反着来注册一下
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        context.refresh();
        System.out.println(context.getBean(Bean1.class).getBean2());
    }
}
