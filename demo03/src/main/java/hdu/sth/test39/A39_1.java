package hdu.sth.test39;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * description: A39_1 <br>
 * date: 2022/8/10 20:05 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class A39_1 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("1. 演示获取 Bean Definition 源");
        SpringApplication spring = new SpringApplication(A39_1.class);
        Set set=new HashSet<>();
        set.add("classpath:b01.xml");
        spring.setSources(set);//定义多个来源，来源于xml

        System.out.println("2. 演示推断应用类型");
        Method deduceFromClasspath = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath");
        deduceFromClasspath.setAccessible(true);
        System.out.println("\t应用类型："+deduceFromClasspath.invoke(null)); //静态方法反射调用无需传递对象
        System.out.println("3. 演示 ApplicationContext 初始化器");
        spring.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                if (applicationContext instanceof GenericApplicationContext){
                    ((GenericApplicationContext) applicationContext).registerBean("bean3",Bean3.class);
                }
            }
        });
        System.out.println("4. 演示监听器与事件");
        spring.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("\t事件："+event.getClass());
            }
        });
        System.out.println("5. 演示主类推断");
        Method deduceMainApplicationClass = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        deduceMainApplicationClass.setAccessible(true);
        System.out.println("\t主类："+deduceMainApplicationClass.invoke(spring));

        ConfigurableApplicationContext context = spring.run(args);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name+":"+context.getBeanFactory().getBeanDefinition(name).getResourceDescription()); //来源
        }

        context.close();
    }

    static class Bean1{}
    static class Bean3{}

    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory(){
        return new TomcatServletWebServerFactory();
    }
}
