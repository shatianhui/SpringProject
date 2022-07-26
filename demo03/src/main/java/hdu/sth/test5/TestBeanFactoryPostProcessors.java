package hdu.sth.test5;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

/**
 * description: TestBeanFactoryPostProcessors <br>
 * date: 2022/7/25 15:15 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class TestBeanFactoryPostProcessors {
    public static void main(String[] args) throws IOException {
        // ⬇️GenericApplicationContext 是一个【干净】的容器，默认不会添加任何后处理器，方便做测试
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean("config", Config.class);
        // 添加Bean工厂后处理器ConfigurationClassPostProcessor
        // 解析@ComponentScan、@Bean、@Import、@ImportResource注解
        //context.registerBean(ConfigurationClassPostProcessor.class);

        // 添加Bean工厂后处理器MapperScannerConfigurer，解析@MapperScan注解
//        context.registerBean(MapperScannerConfigurer.class, beanDefinition -> {
//            // 指定扫描的包名
//            beanDefinition.getPropertyValues().add("basePackage", "hdu.sth.test5.mapper");
//        });

        // 注册自己写的bean工厂后处理器
        // context.registerBean(ComponentScanPostProcessor.class);
        context.registerBean(AtBeanPostProcessor.class);

        context.registerBean(MapperPostProcessor.class);

        // ⬇️初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // ⬇️销毁容器
        context.close();
    }
}
