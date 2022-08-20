package hdu.sth.test42;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * description: A41 <br>
 * date: 2022/8/15 22:00 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A42 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.getDefaultListableBeanFactory().setAllowBeanDefinitionOverriding(false);  // 后注册的bean是否允许覆盖前面的bean(默认为true)
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
    @Configuration
    @Import(MyImportSelector.class)  //引入第三方配置类
    static class Config{  //本项目的配置类
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
    }

    static class MyImportSelector implements DeferredImportSelector{  // DeferredImportSelector  推迟导入，先解析本项目的@Bean,后解析@Import第三方导入的
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {  //方法的返回值为导入第三方配置类的类名
            return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
        }
    }
    static class MyCondition1 implements Condition{
        // 存在Druid依赖则返回true，不存在则返回false
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",null);
        }
    }

    static class MyCondition2 implements Condition{
        // 存在Druid依赖则返回false，不存在则返回true
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",null);
        }
    }

    @Configuration  // 第三方的配置类
    //@Conditional(MyCondition1.class)
    static class AutoConfiguration1{
        @ConditionalOnMissingBean
        @Bean
        @Conditional(MyCondition1.class)
        public Bean1 bean1(){
            return new Bean1();
        }
    }
    static class Bean1{
    }

    @Configuration  // 第三方的配置类
    //@Conditional(MyCondition2.class)
    static class AutoConfiguration2{
        @Bean
        @Conditional(MyCondition2.class)
        public Bean2 bean2(){
            return new Bean2();
        }
    }
    static class Bean2{}
}
