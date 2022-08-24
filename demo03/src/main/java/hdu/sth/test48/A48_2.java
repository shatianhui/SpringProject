package hdu.sth.test48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * description: A48_1 <br>
 * date: 2022/8/23 9:37 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class A48_2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_2.class);
        context.getBean(MyService.class).doBusiness();
        context.close();
    }

    static class MyEvent extends ApplicationEvent{

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
        @EventListener
        public void listener(MyEvent event){
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j
    static class EmailService{
        @EventListener
        public void listener(MyEvent event){
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
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor executor){  //取代默认的单线程事件广播器 ,名字必须为applicationEventMulticaster才会覆盖掉默认的
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }

}
/**
 * 优化：将主线业务和支线业务做成异步（不同线程）
 */