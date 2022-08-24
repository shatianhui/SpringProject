package hdu.sth.test48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description: A48_3 <br>
 * date: 2022/8/23 10:23 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

// @EventListener注解解析原理
@Configuration
public class A48_3 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_3.class);
        context.getBean(MyService.class).doBusiness();
        context.close();
    }

    @Bean
    public SmartInitializingSingleton smartInitializingSingleton(ConfigurableApplicationContext context){   //所有bean准备好调用这个后处理器
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (String name : context.getBeanDefinitionNames()) {  //遍历容器中所有的bean
                    Object bean = context.getBean(name);
                    for (Method method : bean.getClass().getDeclaredMethods()) {
                        if (method.isAnnotationPresent(MyEventListener.class)) {  //底层还是通过实现ApplicationListener的方式
                            ApplicationListener listener=new ApplicationListener() {
                                @Override
                                public void onApplicationEvent(ApplicationEvent event) {
                                    // 事件类型要与监听器方法的参数类型一致才进行反射调用
                                    Class<?> eventType = method.getParameterTypes()[0]; //监听器方法的参数类型
                                    if (eventType.isAssignableFrom(event.getClass())){
                                        try {
                                            method.invoke(bean, event);  //反射调用@MyEnventListener标注的方法
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            };
                            context.addApplicationListener(listener);
                        }
                    }
                }

            }
        };
    }

    static class MyEvent extends ApplicationEvent {

        public MyEvent(Object source) {  //参数表示事件源，谁触发了这个事件
            super(source);
        }
    }

    @Component
    @Slf4j
    static class MyService{
        @Autowired
        private ApplicationEventPublisher publisher;//事件发布器   applicationContext(实现了ApplicationEventPublisher接口)
        public void doBusiness(){
            log.debug("主线业务");
            // 实现主线业务和支线业务低耦合 （通过事件）
            // 主线作为事件的发布者，支线作为事件的接受者（监听器）

            publisher.publishEvent(new MyEvent("MyService.doBusiness().."));
//            log.debug("发送短信");
//            log.debug("发送邮件");
        }
    }

    @Component
    @Slf4j
    static class SmsService{
        @MyEventListener
        public void listener(MyEvent event){
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j
    static class EmailService{
        @MyEventListener
        public void listener(MyEvent event){
            log.debug("发送邮件");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface MyEventListener{
    }

}
