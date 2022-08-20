package hdu.sth.test41;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
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
public class TestAopAuto {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        StandardEnvironment env=new StandardEnvironment();
        env.getPropertySources().addFirst(new SimpleCommandLinePropertySource("--spring.aop.auto=true"));
        context.setEnvironment(env);

        // 注册beanFactory后处理器，bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("config",Config.class);

        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>");
        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean("org.springframework.aop.config.internalAutoProxyCreator", AnnotationAwareAspectJAutoProxyCreator.class);
        System.out.println(creator.isProxyTargetClass());

        /**
         * org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$AspectJAutoProxyingConfiguration$CglibAutoProxyConfiguration
         * org.springframework.aop.config.internalAutoProxyCreator
         * org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$AspectJAutoProxyingConfiguration
         * org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
         */
    }

    @Configuration
    @Import(MyImportSelector.class)
    static class Config{

    }

    static class MyImportSelector implements DeferredImportSelector{

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AopAutoConfiguration.class.getName()};   //加入Aop的自动配置类
        }
    }
}
