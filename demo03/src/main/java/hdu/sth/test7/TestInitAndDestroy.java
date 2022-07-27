package hdu.sth.test7;

import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestInitAndDestory <br>
 * date: 2022/7/27 11:36 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestInitAndDestroy {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        // 解析@PostConstruct注解的bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 解析@Configuration、@Component、@Bean注解的bean工厂后处理器
        context.registerBean(ConfigurationClassPostProcessor.class);

        context.refresh();
        context.close();
    }
}
