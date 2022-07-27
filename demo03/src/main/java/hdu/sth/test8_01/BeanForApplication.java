package hdu.sth.test8_01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * description: BeanForRequest <br>
 * date: 2022/7/27 16:34 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Slf4j
@Scope("application")
@Component
public class BeanForApplication {
    @PreDestroy
    public void destory() {
        log.debug("destroy");
    }
}