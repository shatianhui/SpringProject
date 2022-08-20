package hdu.sth.test41;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * description: TestAopAuto <br>
 * date: 2022/8/16 10:30 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestDataSourceAuto {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        StandardEnvironment env=new StandardEnvironment();
        env.getPropertySources().addLast(new SimpleCommandLinePropertySource(
                "--spring.datasource.url=jdbc:mysql://localhost:3306/test",
                "--spring.datasource.username=root",
                "--spring.datasource.password=mysqladmin"
        ));
        context.setEnvironment(env);

        // 注册beanFactory后处理器，bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("config",Config.class);

        String packageName = TestDataSourceAuto.class.getPackage().getName();
        System.out.println("当前包名："+packageName);
        AutoConfigurationPackages.register(context.getDefaultListableBeanFactory(),packageName);

        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name + " 来源："+context.getBeanDefinition(name).getResourceDescription());
        }

        DataSourceProperties sourceProperties = context.getBean(DataSourceProperties.class);
        System.out.println(sourceProperties.getUrl());
        System.out.println(sourceProperties.getUsername());
        System.out.println(sourceProperties.getPassword());



    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{

    }

    static class MyImportSelector implements DeferredImportSelector{

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{
                    DataSourceAutoConfiguration.class.getName(),
                    MybatisAutoConfiguration.class.getName(),
                    DataSourceTransactionManagerAutoConfiguration.class.getName(),
                    TransactionAutoConfiguration.class.getName()
            };
        }
    }
}
