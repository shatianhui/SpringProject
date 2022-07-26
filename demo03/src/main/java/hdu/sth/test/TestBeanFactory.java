package hdu.sth.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * description: TestBeanFactory <br>
 * date: 2022/7/18 20:45 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestBeanFactory {
    public static void main(String[] args) {
//        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
//        // bean 的定义（即bean的一些描述信息，包含class：bean是哪个类，scope：单例还是多例，初始化、销毁方法等）
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.
//                genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
//        beanFactory.registerBeanDefinition("config", beanDefinition);
//        // 打印beanFactory中的bean
////        for (String name : beanFactory.getBeanDefinitionNames()) {
////            System.out.println(name);
////        }
//        // 打印结果只有config
//        // 那bean1 bean2 也应该在容器中，  @Configuration @Bean这些注解没有起到作用
//
//        // 给BeanFactory添加一些常用的后处理器【beanFactory的扩展功能】，让它具备解析@Configuration、@Bean等注解的能力
//        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory); // 这时，bean工厂就有了这些后处理器的bean,还没起作用
////        for (String name : beanFactory.getBeanDefinitionNames()) {
////            System.out.println(name);
////        }
//        /**
//         * config
//         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor  解析@Configuration @Bean这写注解
//         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor  解析@Autowired @Value注解
//         * org.springframework.context.annotation.internalCommonAnnotationProcessor  解析 @Resource注解（javaEE）
//         * org.springframework.context.event.internalEventListenerProcessor
//         * org.springframework.context.event.internalEventListenerFactory
//         */
//        // 从bean工厂中取出BeanFactory的后处理器
//        // BeanFactory 后处理器主要功能，补充了一些 bean 的定义
//        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
//            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory); //执行BeanFactory的后处理器
//        });
//        // 打印BeanFactory中Bean
////        for (String name : beanFactory.getBeanDefinitionNames()) {
////            System.out.println(name);
////        }
//        /**
//         * config
//         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
//         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
//         * org.springframework.context.annotation.internalCommonAnnotationProcessor
//         * org.springframework.context.event.internalEventListenerProcessor
//         * org.springframework.context.event.internalEventListenerFactory
//         * bean1
//         * bean2
//         */
//        // bean1,bean2也加入了bean工厂，我们看一下可不可以使用
////        Bean1 bean1 = beanFactory.getBean(Bean1.class); //构造 Bean1()
////        System.out.println(bean1.getBean2()); // null
//        // 发现没有依赖注入的功能  【需要添加bean后处理器】
//        // 要想@Autowired、@Resource等注解被解析，还要添加Bean的后处理器，可以针对Bean的生命周期的各个阶段提供扩展
//        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanPostProcessor -> {
//            System.out.println("-------->"+beanPostProcessor);
//            beanFactory.addBeanPostProcessor(beanPostProcessor);
//        });
//        /**
//         * org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@4df828d7
//         * org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@
//         * 先加入解析@Autowired的后处理器，再解析@Resource的后处理器
//         */
//        //Bean1 bean1 = beanFactory.getBean(Bean1.class); //构造 Bean1()   构造 Bean2()
//        //System.out.println(bean1.getBean2()); // hdu.sth.test.Bean2@4450d156
//        // 还要注意的一点是：当第一次调用bean的时候，才会去创建该bean的实例 （默认延迟实例化）
//        // 要想提前把bean对象创建好，我们可以调用下面的方法
//        //beanFactory.preInstantiateSingletons();// 准备好所有的单例
//
//        //System.out.println(beanFactory.getBean(Bean1.class).getBean3());

    }
}

//@Configuration
//class Config {
//    @Bean
//    public Bean1 bean1() {
//        return new Bean1();
//    }
//
//    @Bean
//    public Bean2 bean2() {
//        return new Bean2();
//    }
//
//    @Bean
//    public Bean3 bean3(){
//        return new Bean3();
//    }
//
//    @Bean
//    public Bean4 bean4(){
//        return new Bean4();
//    }
//
//}
//
//@Slf4j
//class Bean1 {
//    @Autowired
//    private Bean2 bean2;
//
////    @Autowired
////    private Inter inter;  //这样写一定会注入失败，因为Iter类型的bean不止一个
//
//    @Autowired
//    @Resource(name = "bean4")  //注入bean4
//    private Inter bean3;   //两个都加，注入bean3,跟后处理器的顺序有关
//
//    public Inter getBean3() {
//        return bean3;
//    }
//
//    public Bean2 getBean2() {
//        return bean2;
//    }
//
//    public Bean1() {
//        log.debug("构造 Bean1()");
//    }
//}
//
//@Slf4j
//class Bean2 {
//    public Bean2() {
//        log.debug("构造 Bean2()");
//    }
//}
//
//interface Inter {
//
//}
//
//@Slf4j
//class Bean3 implements Inter {
//    public Bean3() {
//        log.debug("构造 Bean3()");
//    }
//}
//
//@Slf4j
//class Bean4 implements Inter {
//    public Bean4() {
//        log.debug("构造 Bean4()");
//    }
//}