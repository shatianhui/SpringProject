package hdu.sth.test41;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * description: A41 <br>
 * date: 2022/8/15 22:00 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A41 {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.getDefaultListableBeanFactory().setAllowBeanDefinitionOverriding(false);  // 后注册的bean是否允许覆盖前面的bean(默认为true)
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(context.getBean(Bean1.class));
    }
    @Configuration
    @Import(MyImportSelector.class)  //引入第三方配置类
    static class Config{  //本项目的配置类
        @Bean
        public Bean1 bean1(){
            return new Bean1("本项目");
        }
    }

//    static class MyImportSelector implements ImportSelector{
//        @Override
//        public String[] selectImports(AnnotationMetadata importingClassMetadata) {  //方法的返回值为导入第三方配置类的类名
//            // 获取spring.factories文件中的值
//            List<String> list = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
//            return list.toArray(new String[0]);
//        }
//    }

        static class MyImportSelector implements DeferredImportSelector{  // DeferredImportSelector  推迟导入，先解析本项目的@Bean,后解析@Import第三方导入的
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {  //方法的返回值为导入第三方配置类的类名
            // 获取spring.factories文件中的值
            List<String> list = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
            return list.toArray(new String[0]);
        }
    }
    @Configuration  // 第三方的配置类
    static class AutoConfiguration1{
        @ConditionalOnMissingBean  //当本项目没有时，下面的bean1才生效
        @Bean
        public Bean1 bean1(){
            return new Bean1("第三方");
        }
    }
    static class Bean1{
        String name;
        public Bean1(String name){
            this.name=name;
        }

        @Override
        public String toString() {
            return "Bean1{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Configuration  // 第三方的配置类
    static class AutoConfiguration2{
        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }
    static class Bean2{}
}
