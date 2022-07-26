package hdu.sth.test5;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

/**
 * description: MapperPostProcessor <br>
 * date: 2022/7/26 15:27 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class MapperPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        try {
            // 扫描包下的资源
            String path = "classpath*:hdu/sth/test5/mapper/**/*.class";
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
            AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
            // 判断这个资源是不是接口
            for (Resource resource : resources) {
                MetadataReader reader = factory.getMetadataReader(resource);
                ClassMetadata classMetadata = reader.getClassMetadata();
                if (classMetadata.isInterface()){
                    // 创建Bean的定义  【注意：类型是MapperFactoryBean,而不是Mapper】 加入构造方法的参数以及要设置sqlSessionFactory
                    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.
                            genericBeanDefinition(MapperFactoryBean.class).
                            addConstructorArgValue(classMetadata.getClassName()).
                    setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE).getBeanDefinition();
                    // 这个beanDefinition 只是为了生成bean的名字，最后不需要加入到beanFactory
                    AbstractBeanDefinition bd = BeanDefinitionBuilder.
                            genericBeanDefinition(classMetadata.getClassName()).
                            addConstructorArgValue(classMetadata.getClassName()).
                            setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE).getBeanDefinition();
                    // 将Bean定义加入工厂
                    String beanName = generator.generateBeanName(bd, beanFactory);
                    beanFactory.registerBeanDefinition(beanName, beanDefinition);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
