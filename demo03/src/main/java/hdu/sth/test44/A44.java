package hdu.sth.test44;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * description: A44 <br>
 * date: 2022/8/18 19:36 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

/*
    做这个试验前, 先在 target/classes 创建 META-INF/spring.components, 内容为

    com.itheima.a44.Bean1=org.springframework.stereotype.Component
    com.itheima.a44.Bean2=org.springframework.stereotype.Component

    做完实现建议删除, 避免影响其它组件扫描的结果

    真实项目中, 这个步骤可以自动完成, 加入以下依赖
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-indexer</artifactId>
        <optional>true</optional>
    </dependency>
 */
public class A44 {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();

        // 组件扫描的核心类
        ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.scan(A44.class.getPackage().getName());

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
