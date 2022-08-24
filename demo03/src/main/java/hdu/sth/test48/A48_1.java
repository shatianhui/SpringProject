package hdu.sth.test48;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * description: A48_1 <br>
 * date: 2022/8/23 9:37 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class A48_1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_1.class);
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

}
