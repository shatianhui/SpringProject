package hdu.sth.test5;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * description: AtBeanPostProcessor <br>
 * date: 2022/7/26 15:01 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class AtBeanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
        try {
            MetadataReader reader = factory.getMetadataReader(new ClassPathResource("hdu/sth/test5/Config.class")); //读取配置文件
            // 获取所有被@Bean注解标注的方法信息
            Set<MethodMetadata> annotatedMethods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
            for (MethodMetadata annotatedMethod : annotatedMethods) {
                // System.out.println(annotatedMethod);

                // 对于@Bean属性的解析
                String initMethod = annotatedMethod.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();

                // 根据方法信息，创建beanDefinition
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
                builder.setFactoryMethodOnBean(annotatedMethod.getMethodName(), "config");
                // 指定装配模式，对于构造方法参数和工厂方法参数的装配模式是AUTOWIRE_CONSTRUCTOR
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

                if (initMethod.length() > 0) {
                    builder.setInitMethodName(initMethod);
                }
                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                if (configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                    DefaultListableBeanFactory beanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
                    beanFactory.registerBeanDefinition(annotatedMethod.getMethodName(), beanDefinition);// 方法的名字作为bean的名字
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
