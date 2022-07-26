package hdu.sth.test2_02;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * description: TestApplicationContext <br>
 * date: 2022/7/19 11:00 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestApplicationContext {

    public static void main(String[] args) throws IOException {
        // testClassPathXmlApplicationContext();
        // testFileSystemXmlApplicationContext();  //基于xml的spring 容器
        // testAnnotationConfigApplicationContext();  // 非web环境 基于配置类的spring容器
        // testAnnotationConfigServletWebServerApplicationContext(); //web环境 基于配置类的spring容器

        //模拟一下ClassPathXmlApplicationContext、FileSystemXmlApplicationContext底层的一些操作
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
        System.out.println("读取之前...");
        // 打印beanFactory中bean的定义
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name); //什么都没有
        }
        System.out.println("读取之后...");
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
        // 加载bean的定义信息（来自xml文件）
        reader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name); // bean1 bean2
        }
    }

    // 1.基于classpath 下 xml 格式的配置文件来创建
    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        /**
         * bean1
         * bean2
         */
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 2.基于磁盘路径下 xml 格式的配置文件来创建
    private static void testFileSystemXmlApplicationContext() {
        //System.out.println(System.getProperty("user.dir"));
        // 可以用绝对路径或者相对路径
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("demo03\\src\\main\\resources\\applicationContext.xml");
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 3.基于java配置类来创建
    private static void testAnnotationConfigApplicationContext() {
        // 会自动加上5个后处理器
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
        // org.springframework.context.annotation.internalCommonAnnotationProcessor
        // org.springframework.context.event.internalEventListenerProcessor
        // org.springframework.context.event.internalEventListenerFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name); //5个后处理器，config, bean1, bean2
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 4.基于java配置类来创建，用于web环境
    // 模拟了 springboot web项目  内嵌Tomcat
    private static void testAnnotationConfigServletWebServerApplicationContext() throws IOException {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

}





