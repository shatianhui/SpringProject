package hdu.sth.test47;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * description: A47_2 <br>
 * date: 2022/8/20 11:08 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class A47_2 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47_2.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        //处理数组
        //testArray(beanFactory);
        //处理集合
        //testList(beanFactory);
        //处理ApplicationContext
        //testApplicationContext(beanFactory);
        //处理泛型
        //testGeneric(beanFactory);
        //处理@Qualifier
        testQualifier(beanFactory);

    }

    private static void testQualifier(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException, IllegalAccessException {
        DependencyDescriptor dd = new DependencyDescriptor(Target.class.getDeclaredField("service"), false);
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dd.getDependencyType());
        // ① @Value ② @Lazy  ③泛型对比  ④解析@Qualifier
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);
        for (String name : names) {
            //System.out.println(name);
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(name);
            // 对比beanDefinition的bean的名字与 DependencyDescriptor中的@Qualifier是否一致
            if (resolver.isAutowireCandidate(new BeanDefinitionHolder(beanDefinition,name),dd)) {
                System.out.println(name);
                System.out.println(beanFactory.getBean(name));
            }
        }
    }

    private static void testGeneric(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException, IllegalAccessException {
        DependencyDescriptor dd = new DependencyDescriptor(Target.class.getDeclaredField("dao"), false);
        String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, dd.getDependencyType());
        // ① @Value ② @Lazy  ③泛型对比  ④解析@Qualifier
        ContextAnnotationAutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);
        for (String name : names) {
            //System.out.println(name);
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(name);
            // 对比beanDefinition的泛型与 DependencyDescriptor中的泛型是否一致
            if (resolver.isAutowireCandidate(new BeanDefinitionHolder(beanDefinition,name),dd)) {
                System.out.println(name);
                System.out.println(beanFactory.getBean(name));
            }
        }
    }
    private static void testApplicationContext(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException, IllegalAccessException {
        DependencyDescriptor dd = new DependencyDescriptor(Target.class.getDeclaredField("applicationContext"), false);

        Field resolvableDependencies = DefaultListableBeanFactory.class.getDeclaredField("resolvableDependencies");
        resolvableDependencies.setAccessible(true);

        Map<Class<?>, Object> map = (Map<Class<?>, Object>)resolvableDependencies.get(beanFactory);
//        map.forEach((k,v)->{
//            System.out.println(k+"-->"+v);
//        });
//
        for (Map.Entry<Class<?>, Object> entry : map.entrySet()) {
            if (entry.getKey().isAssignableFrom(dd.getDependencyType())) {  //右边的类型是否能够赋值给左边的类型
                System.out.println(entry.getValue());
                break;
            }
        }
    }

    private static void testList(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException {
        DependencyDescriptor dd = new DependencyDescriptor(Target.class.getDeclaredField("serviceList"), false);
        if (dd.getDependencyType()==List.class) {
            //获取List上泛型的参数类型
            Class<?> componentType = dd.getResolvableType().getGeneric().resolve();
            System.out.println(componentType);
            // 根据类型查找容器中所有这个类型的bean的名字
            String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, componentType);
            //System.out.println(Arrays.toString(beanNames));
            List<Object> beans = new ArrayList<>();
            for (String beanName : beanNames) {
                Object bean = beanFactory.getBean(beanName);  //得到bean对象
                beans.add(bean);
            }
            System.out.println(beans);
        }
    }
    private static void testArray(DefaultListableBeanFactory beanFactory) throws NoSuchFieldException {
        DependencyDescriptor dd = new DependencyDescriptor(Target.class.getDeclaredField("serviceArray"), false);
        if (dd.getDependencyType().isArray()) { //是否是数组
            //获取元素类型
            Class<?> componentType = dd.getDependencyType().getComponentType();
            // 根据类型查找容器中所有这个类型的bean的名字
            String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, componentType);
            //System.out.println(Arrays.toString(beanNames));
            List<Object> beans = new ArrayList<>();
            for (String beanName : beanNames) {
                Object bean = beanFactory.getBean(beanName);  //得到bean对象
                beans.add(bean);
            }
            //List转换成数组
            Object array = beanFactory.getTypeConverter().convertIfNecessary(beans, dd.getDependencyType());

        }
    }

    static class Target {
        @Autowired
        private Service[] serviceArray;
        @Autowired
        private List<Service> serviceList;
        @Autowired
        private ConfigurableApplicationContext applicationContext;
        @Autowired
        private Dao<Teacher> dao;
        @Autowired @Qualifier("service2")
        private Service service;
    }
    interface Dao<T> {
    }
    @Component("dao1")
    static class Dao1 implements Dao<Student> {
    }
    @Component("dao2")
    static class Dao2 implements Dao<Teacher> {
    }

    static class Student {
    }

    static class Teacher {
    }

    interface Service {
    }

    @Component("service1")
    static class Service1 implements Service {
    }

    @Component("service2")
    static class Service2 implements Service {
    }

    @Component("service3")
    static class Service3 implements Service {
    }
}
