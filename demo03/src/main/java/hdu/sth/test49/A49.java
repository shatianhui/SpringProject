package hdu.sth.test49;

import hdu.sth.test48.A48_1;
import hdu.sth.test48.A48_2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * description: A49 <br>
 * date: 2022/8/24 9:30 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class A49 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A49.class);
        context.getBean(MyService.class).doBusiness();
        context.close();
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
    static class SmsApplicationListener implements ApplicationListener<MyEvent> { //泛型用来对事件进行筛选，只关注MyEvent类型的事件
        @Override
        public void onApplicationEvent(MyEvent event) {  //事件发生后会执行的方法
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j
    static class EmailApplicationListener implements ApplicationListener<MyEvent> { //泛型用来对事件进行筛选，只关注MyEvent类型的事件
        @Override
        public void onApplicationEvent(MyEvent event) {  //事件发生后会执行的方法
            log.debug("发送邮件");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor executor(){  //创建线程池对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableApplicationContext context,ThreadPoolTaskExecutor executor){
        return new AbstractApplicationEventMulticaster(){
            private List<GenericApplicationListener> listeners = new ArrayList<>();
            //收集监听器
            @Override
            public void addApplicationListenerBean(String listenerBeanName) {
                //System.out.println(listenerBeanName);
                ApplicationListener listener = context.getBean(listenerBeanName, ApplicationListener.class);
                //获取该监听器实现接口的泛型（事件类型）
                ResolvableType type = ResolvableType.forClass(listener.getClass()).getInterfaces()[0].getGeneric();
                System.out.println(type);
                //将原始的listener 封装成支持事件检查的listener
                GenericApplicationListener genericApplicationListener=new GenericApplicationListener() {
                    //是否支持某事件的类型
                    @Override
                    public boolean supportsEventType(ResolvableType eventType) { //参数为真实的事件类型
                        return type.isAssignableFrom(eventType);
                    }
                    // supportsEventType返回true调用下面的方法
                    @Override
                    public void onApplicationEvent(ApplicationEvent event) {
                        listener.onApplicationEvent(event);
                    }
                };
                listeners.add(genericApplicationListener);
            }

            //发事件
            @Override
            public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
                for (GenericApplicationListener listener : listeners) {
                    if (listener.supportsEventType(ResolvableType.forType(event.getClass()))){
                        executor.submit(() -> listener.onApplicationEvent(event));
                    }
                }
            }

        };
    }
   abstract static class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

       @Override
       public void addApplicationListener(ApplicationListener<?> listener) {

       }

       @Override
       public void addApplicationListenerBean(String listenerBeanName) {

       }

       @Override
       public void removeApplicationListener(ApplicationListener<?> listener) {

       }

       @Override
       public void removeApplicationListenerBean(String listenerBeanName) {

       }

       @Override
       public void removeApplicationListeners(Predicate<ApplicationListener<?>> predicate) {

       }

       @Override
       public void removeApplicationListenerBeans(Predicate<String> predicate) {

       }

       @Override
       public void removeAllListeners() {

       }

       @Override
       public void multicastEvent(ApplicationEvent event) {

       }

       @Override
       public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {

       }
   }

}
