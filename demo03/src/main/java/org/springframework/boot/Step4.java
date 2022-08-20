package org.springframework.boot;

import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * description: Step4 <br>
 * date: 2022/8/11 20:42 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Step4 {
    public static void main(String[] args) throws IOException {
        ApplicationEnvironment environment = new ApplicationEnvironment();
        environment.getPropertySources().addLast(new ResourcePropertySource("classpath:step4.properties"));
        ConfigurationPropertySources.attach(environment);  //ConfigurationPropertySourcesPropertySource放在最前面
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            System.out.println(propertySource);
        }

        System.out.println(environment.getProperty("user.first-name"));
        System.out.println(environment.getProperty("user.middle-name"));
        System.out.println(environment.getProperty("user.last-name"));
    }
}
