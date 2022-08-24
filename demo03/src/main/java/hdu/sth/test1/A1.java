package hdu.sth.test1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * description: A1 <br>
 * date: 2022/8/24 10:58 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class A1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A1.class);
        context.getBean(Component2.class).register();
        context.close();
    }

    static class UserRegisterEvent extends ApplicationEvent{
        public UserRegisterEvent(Object source) {
            super(source);
        }
    }

    @Component
    @Slf4j
    static class Component1{
        @EventListener
        public void listener(UserRegisterEvent event){
            log.debug("{}",event);
            log.debug("发送短信");
        }
    }

    @Component
    @Slf4j
    static class Component2{
        @Autowired
        private ApplicationEventPublisher publisher;
        public void register(){
            log.debug("用户注册");
            publisher.publishEvent(new UserRegisterEvent(this));  //发送事件
        }
    }
}
