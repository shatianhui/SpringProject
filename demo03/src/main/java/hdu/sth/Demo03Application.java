package hdu.sth;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class Demo03Application {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        // Spring 容器
        ConfigurableApplicationContext context = SpringApplication.run(Demo03Application.class, args);
        context.close();
    }

}
