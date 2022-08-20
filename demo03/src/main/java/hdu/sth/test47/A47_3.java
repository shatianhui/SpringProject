package hdu.sth.test47;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * description: A47_3 <br>
 * date: 2022/8/20 16:20 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class A47_3 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47_3.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        // @Primary
//        testPrimary(beanFactory);
        // 成员变量名字与bean的名字进行匹配
        testDefault(beanFactory);
    }

    private static void testDefault(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException, IllegalAccessException {
        DependencyDescriptor dd = new DependencyDescriptor(A47_3.Target2.class.getDeclaredField("service3"), false);
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dd.getDependencyType());
        for (String name : names) {
           if (name.equals(dd.getDependencyName())){  //员变量的名字与bean的名字进行匹配
               System.out.println(name);
           }
        }
    }

    private static void testPrimary(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException, IllegalAccessException {
        DependencyDescriptor dd = new DependencyDescriptor(A47_3.Target1.class.getDeclaredField("service"), false);
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dd.getDependencyType());
        for (String name : names) {
            if (beanFactory.getBeanDefinition(name).isPrimary()) {
                System.out.println(name);
            }
        }
    }

    static class Target1 {
        @Autowired
        private Service service;  //除了使用@Qualifier注解外，我们也可以通过@Primary
    }

    static class Target2 {
        @Autowired
        private Service service3;  //根据成员变量的名字与bean的名字进行匹配
    }

    interface Service { }
    @Component("service1")
    static class Service1 implements Service {
    }
    @Component("service2")
    @Primary  //优先注入
    static class Service2 implements Service {
    }
    @Component("service3")
    static class Service3 implements Service {
    }
}
