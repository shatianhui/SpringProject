package hdu.sth.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * description: OrderCreateEvent <br>
 * date: 2022/8/11 11:10 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class OrderCreateEvent extends ApplicationEvent {
    private Object log;
    public OrderCreateEvent(Object source,Object log) {  //source是事件源对象
        super(source);
        this.log=log;
    }

}
