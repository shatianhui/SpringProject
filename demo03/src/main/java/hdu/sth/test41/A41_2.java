package hdu.sth.test41;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * description: A41 <br>
 * date: 2022/8/15 22:00 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A41_2 {
//    public static void main(String[] args) {
//        GenericApplicationContext context=new GenericApplicationContext();
//        StandardEnvironment env=new StandardEnvironment();
//        env.getPropertySources().addLast(new SimpleCommandLinePropertySource(
//                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
//                "--spring.datasource.username=root",
//                "--spring.datasource.password=mysqladmin"
//        ));
//        context.setEnvironment(env);
//
//
//        context.getDefaultListableBeanFactory().setAllowBeanDefinitionOverriding(false);  // 后注册的bean是否允许覆盖前面的bean(默认为true)
//        context.registerBean("config",Config.class);
//        context.registerBean(ConfigurationClassPostProcessor.class);
//        context.refresh();
//
//        for (String name : context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//
//    }
//    @Configuration
//    //@Import(MyImportSelector.class)  //引入第三方配置类
//    @EnableAutoConfiguration
//    static class Config{  //本项目的配置类
//
//    }
//
//    static class MyImportSelector implements DeferredImportSelector{  // DeferredImportSelector  推迟导入，先解析本项目的@Bean,后解析@Import第三方导入的
//        @Override
//        public String[] selectImports(AnnotationMetadata importingClassMetadata) {  //方法的返回值为导入第三方配置类的类名
//            // 获取spring.factories文件中的值
//            List<String> list = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
//            return list.toArray(new String[0]);
//        }
//    }
//    @Configuration  // 第三方的配置类
//    static class AutoConfiguration1{
//        @Bean
//        public Bean1 bean1(){
//            return new Bean1();
//        }
//    }
//    static class Bean1{ }
//    @Configuration  // 第三方的配置类
//    static class AutoConfiguration2{
//        @Bean
//        public Bean2 bean2(){
//            return new Bean2();
//        }
//    }
//    static class Bean2{}
}
