package hdu.sth.test8_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * description: E <br>
 * date: 2022/7/27 19:32 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class E {

    private F1 f1;
    private F2 f2;

    @Autowired
    private ObjectFactory<F3> f3;

    @Autowired
    private ApplicationContext context;

    public E() {
        log.info("E()");
    }

    @Autowired
    @Lazy
    public void setF1(F1 f1) {
        this.f1 = f1;
        log.info("setF(F f) {}", f1.getClass());
    }

    @Autowired
    public void setF2(F2 f2) {
        this.f2 = f2;
        log.info("setF(F f) {}", f2.getClass());
    }

    public F1 getF1() {
        return f1;
    }

    public F2 getF2() {
        return f2;
    }

    public F3 getF3(){
        return f3.getObject();
    }

    public F4 getF4(){
        return context.getBean(F4.class);
    }
}
