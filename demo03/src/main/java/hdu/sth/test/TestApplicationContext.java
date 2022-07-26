package hdu.sth.test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

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

//        //模拟一下ClassPathXmlApplicationContext、FileSystemXmlApplicationContext底层的一些操作
//        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
//        System.out.println("读取之前...");
//        // 打印beanFactory中bean的定义
//        for (String name : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(name); //什么都没有
//        }
//        System.out.println("读取之后...");
//        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
//        // 加载bean的定义信息（来自xml文件）
//        reader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
//        for (String name : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(name); // bean1 bean2
//        }
    }

//    // 1.基于classpath 下 xml 格式的配置文件来创建
//    private static void testClassPathXmlApplicationContext(){
//        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//        /**
//         * bean1
//         * bean2
//         */
//        System.out.println(context.getBean(Bean2.class).getBean1());
//    }
//
//    // 2.基于磁盘路径下 xml 格式的配置文件来创建
//    private static void testFileSystemXmlApplicationContext() {
//        //System.out.println(System.getProperty("user.dir"));
//        // 可以用绝对路径或者相对路径
//        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("demo03\\src\\main\\resources\\applicationContext.xml");
//        System.out.println(context.getBean(Bean2.class).getBean1());
//    }
//
//    // 3.基于java配置类来创建
//    private static void testAnnotationConfigApplicationContext() {
//        // 会自动加上5个后处理器
//        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor
//        // org.springframework.context.annotation.internalAutowiredAnnotationProcessor
//        // org.springframework.context.annotation.internalCommonAnnotationProcessor
//        // org.springframework.context.event.internalEventListenerProcessor
//        // org.springframework.context.event.internalEventListenerFactory
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name); //5个后处理器，config, bean1, bean2
//        }
//        System.out.println(context.getBean(Bean2.class).getBean1());
//    }
//
//    // 4.基于java配置类来创建，用于web环境
//    // 模拟了 springboot web项目  内嵌Tomcat
//    private static void testAnnotationConfigServletWebServerApplicationContext() throws IOException {
//        AnnotationConfigServletWebServerApplicationContext context =
//                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
//    }

}

//@Configuration
//class WebConfig {
//    @Bean
//    // 1. WebServer工厂   Tomcat WebServer服务器   内嵌tomcat
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
//
//    @Bean
//    // 2. web项目必备的DispatcherServlet
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }
//
//    @Bean
//    // 3. 将DispatcherServlet注册到WebServer上
//    public DispatcherServletRegistrationBean
//    dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
//        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
//        // 表示所有的请求都要交给dispatcherServlet来处理,再由DispatcherServlet分发给其他控制器
//    }
//
//    @Bean("/hello")  //访问路径
//    public Controller controller1() {
//        return new Controller() {
//            @Override
//            public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
//                response.getWriter().write("hello spring");
//                return null;
//            }
//        };
//    }
//}
//
//
//@Configuration
//class Config {
//    @Bean
//    public Bean1 bean1() {
//        return new Bean1();
//    }
//
//    @Bean
//    public Bean2 bean2(Bean1 bean1) {
//        Bean2 bean2 = new Bean2();
//        bean2.setBean1(bean1);
//        return bean2;
//    }
//}
//
//
//class Bean1 {
//
//}
//
//class Bean2 {
//    private Bean1 bean1;
//
//    public Bean1 getBean1() {
//        return bean1;
//    }
//
//    public void setBean1(Bean1 bean1) {
//        this.bean1 = bean1;
//    }
//}
