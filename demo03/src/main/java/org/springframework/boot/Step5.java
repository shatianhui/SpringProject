package org.springframework.boot;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.test.context.event.EventPublishingTestExecutionListener;

import javax.swing.*;
import java.util.List;

/**
 * description: Step5 <br>
 * date: 2022/8/12 20:24 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Step5 {
    public static void main(String[] args) {
        // 后处理器实现类的名字是放在配置文件中的，而不是硬编码在java代码中
        SpringApplication spring=new SpringApplication();
//        List<String> names = SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, Step5.class.getClassLoader());
//        System.out.println(names);
        spring.addListeners(new EnvironmentPostProcessorApplicationListener());

        // 由事件发布器触发监听器
        EventPublishingRunListener publisher = new EventPublishingRunListener(spring, args);
        ApplicationEnvironment env = new ApplicationEnvironment();
        System.out.println("===================增强前");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        publisher.environmentPrepared(new DefaultBootstrapContext(), env); //
        System.out.println("====================增强后");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
    }
    private static void test1(){
        SpringApplication spring = new SpringApplication();
        ApplicationEnvironment env = new ApplicationEnvironment();
        // 为environment添加后处理器，增加PropertiesSources源
        System.out.println("===================增强前");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        ConfigDataEnvironmentPostProcessor postProcessor1 = new ConfigDataEnvironmentPostProcessor(new DeferredLogs(),new DefaultBootstrapContext());
        postProcessor1.postProcessEnvironment(env,spring);
        System.out.println("====================增强后");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        System.out.println(env.getProperty("spring.datasource.username"));

        RandomValuePropertySourceEnvironmentPostProcessor postProcessor2 = new RandomValuePropertySourceEnvironmentPostProcessor(new DeferredLog());
        postProcessor2.postProcessEnvironment(env,spring);
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        //产生随机整数
        System.out.println(env.getProperty("random.int"));
        System.out.println(env.getProperty("random.int"));
        System.out.println(env.getProperty("random.uuid"));
        System.out.println(env.getProperty("random.uuid"));
    }
}
