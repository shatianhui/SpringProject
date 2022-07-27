package hdu.sth.test8_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * description: F1 <br>
 * date: 2022/7/27 20:01 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Component
@Scope("prototype")
@Slf4j
public class F4 {
    public F4() {
        log.info("F4()");
    }
}