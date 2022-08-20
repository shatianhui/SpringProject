package hdu.sth.test39;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * description: A39_2 <br>
 * date: 2022/8/11 11:30 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A39_2 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SpringApplication spring=new SpringApplication();

        // 添加监听器
        spring.addListeners(event -> System.out.println(event.getClass()));

        // Step1:创建SpringApplicationRunListener事件发布器

        // 从spring配置文件中获取SpringApplicationRunListener发布器的实现类名称
        List<String> list = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, A39_2.class.getClassLoader()); //返回实现类名称的一个集合
        for (String s : list) {
            System.out.println(s);  //org.springframework.boot.context.event.EventPublishingRunListener

            Class<?> aClass = Class.forName(s);
            Constructor<?> constructor = aClass.getConstructor(SpringApplication.class, String[].class);
            SpringApplicationRunListener publisher = (SpringApplicationRunListener) constructor.newInstance(spring, args);

            //发布事件  7个
            DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

            publisher.starting(bootstrapContext);  //springboot开始启动
            publisher.environmentPrepared(bootstrapContext,new StandardEnvironment()); //环境信息准备完毕
            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context);//spring容器创建，并调用初始化器之后，发布此事件
            publisher.contextLoaded(context); //所有bean definition 加载完毕
            context.refresh();
            publisher.started(context); //spring容器初始化完成（refresh方法调用完毕）
            publisher.running(context); //spring boot启动完毕

            publisher.failed(context,new Exception("出错了！"));
        }
    }
}
