package hdu.sth.test5;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * description: ComponentScanPostProcessor <br>
 * date: 2022/7/26 11:21 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 模拟@ComponentScan注解的解析
        // 查看指定类是否有@ComponentScan注解
        ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
        try {
            if (componentScan != null) {  //该注解不为空
                for (String basePage : componentScan.basePackages()) {
                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();

                    // System.out.println(basePage); //拿到该注解要扫描的包名
                    // 将包名转化为对应的路径
                    // hdu.sth.test5.component  --->  classpath*:hdu/sth/test5/component/**/*.class
                    String path = "classpath*:" + basePage.replace('.', '/') + "/**/*.class";
                    //Resource[] resources = context.getResources(path); // 获得该路径下的所有资源文件
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();  //生成bean的名字
                    //DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
                    String name = Component.class.getName();
                    for (Resource resource : resources) {
                        //System.out.println(resource);
                        // 下面就要查看对应的类上是否有@Component注解
                        MetadataReader reader = factory.getMetadataReader(resource);
                        // System.out.println("类名:"+reader.getClassMetadata().getClassName());// 类名称
                        // System.out.println("是否加了@Component注解"+reader.getAnnotationMetadata().hasAnnotation(Component.class.getName()));
                        // 对于@Controller这种派生注解也应该被扫描到  而@Controller其实就是间接使用了@Controller注解
                        // System.out.println("是否加了@Component派生注解"+reader.getAnnotationMetadata().hasMetaAnnotation(Component.class.getName()));
                        AnnotationMetadata metadata = reader.getAnnotationMetadata();
                        String className=reader.getClassMetadata().getClassName();
                        if (metadata.hasAnnotation(name)
                                || metadata.hasMetaAnnotation(name)){ //如果直接或者间接加了@Component注解
                            // 创建Bean的定义
                            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
                            if (configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                                DefaultListableBeanFactory beanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
                                String beanName = generator.generateBeanName(beanDefinition, beanFactory);
                                // 将Bean定义加入工厂
                                beanFactory.registerBeanDefinition(beanName, beanDefinition);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
