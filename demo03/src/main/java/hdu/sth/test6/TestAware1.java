package hdu.sth.test6;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestAware <br>
 * date: 2022/8/6 21:17 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestAware1 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("myBean",MyBean.class);

        // 解析 @Autowired 注解的Bean后处理器
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 解析 @PostConstruct 注解的Bean后处理器
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.refresh();
        context.close();
    }
}
