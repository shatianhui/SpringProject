package hdu.sth.test6;

import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestAware_MyConfig2 <br>
 * date: 2022/8/6 21:56 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestAware_MyConfig2 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("myConfig2",MyConfig2.class);
        // 解析@ComponentScan、@Bean、@Import、@ImportResource注解的后处理器
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        context.close();
    }
}
