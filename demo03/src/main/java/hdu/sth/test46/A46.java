package hdu.sth.test46;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * description: A46 <br>
 * date: 2022/8/19 20:39 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class A46 {
    public static void main(String[] args) throws NoSuchFieldException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A46.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        ContextAnnotationAutowireCandidateResolver resolver=new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);

        Field name = Bean1.class.getDeclaredField("name");
        Field age = Bean1.class.getDeclaredField("age");
        //test1(context,resolver,name);
//        test2(context,resolver,age);
//        test3(context,resolver,Bean2.class.getDeclaredField("bean3"));
        test3(context,resolver,Bean4.class.getDeclaredField("value"));
    }

    //解析bean2的成员变量bean3
    private static void test3(AnnotationConfigApplicationContext context,ContextAnnotationAutowireCandidateResolver resolver,Field field){
        // 获取@Value原始值的内容
        DependencyDescriptor dd = new DependencyDescriptor(field,true);
        String value = resolver.getSuggestedValue(dd).toString();
        System.out.println(value);
        //1. 对${}进行解析
        value = context.getEnvironment().resolvePlaceholders(value);
        System.out.println(value);
        //2. 解析#{SPL}
        Object bean3 = context.getBeanFactory().getBeanExpressionResolver().evaluate(value, new BeanExpressionContext(context.getBeanFactory(), null));

        //3. 如果类型不一致，进行转换
        Object result = context.getBeanFactory().getTypeConverter().convertIfNecessary(bean3, dd.getDependencyType());//传入转换的值以及要转换的类型
        System.out.println(result);
    }

    //解析bean1的成员变量age
    private static void test2(AnnotationConfigApplicationContext context,ContextAnnotationAutowireCandidateResolver resolver,Field field){
        DependencyDescriptor dd = new DependencyDescriptor(field,true);
        String value = resolver.getSuggestedValue(dd).toString();
        System.out.println(value);
        // 对${}进行解析
        value = context.getEnvironment().resolvePlaceholders(value);
        System.out.println(value);
        System.out.println(value.getClass());
        // 类型不一致，进行转换
        Object age = context.getBeanFactory().getTypeConverter().convertIfNecessary(value, dd.getDependencyType());//传入转换的值以及要转换的类型
        System.out.println(age.getClass());
    }

    //解析bean1的成员变量name
    private static void test1(AnnotationConfigApplicationContext context,ContextAnnotationAutowireCandidateResolver resolver,Field field){
        DependencyDescriptor dd = new DependencyDescriptor(field,true);
        String value = resolver.getSuggestedValue(dd).toString();//获取Bean1中成员变量home中 @Value中的内容
        System.out.println(value);//${JAVA_HOME}
        // 对${}进行解析
        value = context.getEnvironment().resolvePlaceholders(value);
        System.out.println(value);  //E:\Java\jdk1.8.0_311
    }

    public class Bean1{
        @Value("${JAVA_HOME}")
        private String name;
        @Value("18")
        public Integer age;
    }

    public class Bean2 {
        @Value("#{@bean3}") // SpringEL       #{SpEL}   @表示根据bean的名字找，找名字为bean3的bean
        private Bean3 bean3;
    }

    @Component("bean3")
    public class Bean3 {
    }

    static class Bean4 {
        @Value("#{'hello, ' + '${JAVA_HOME}'}")
        private String value;
    }
}
