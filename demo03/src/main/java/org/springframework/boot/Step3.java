package org.springframework.boot;

import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * description: test39  Step3 <br>
 * date: 2022/8/11 20:10 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Step3 {
    public static void main(String[] args) throws IOException {
        // step3:准备Environment  环境信息来源于系统环境变量，properties文件，yml等
        // 作用：将多个环境源封装到一起
        ApplicationEnvironment environment = new ApplicationEnvironment();
//        for (PropertySource<?> propertySource : environment.getPropertySources()) {
//            System.out.println(propertySource);  //两类 ①SystemProperties系统属性 ②systemEnvironment系统环境变量
//        }
        // 刚开始的ApplicationContext没有properties的来源  我们可以往里面加
        environment.getPropertySources().addLast(new ResourcePropertySource("classpath:application.properties"));
        // 添加命令行来源
        environment.getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));
        System.out.println(environment.getProperty("JAVA_HOME")); // 从前往后找，找到了直接返回，就不再往后找了
        System.out.println(environment.getProperty("server.port"));
    }
}
