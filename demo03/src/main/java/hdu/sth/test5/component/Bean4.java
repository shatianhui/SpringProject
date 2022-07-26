package hdu.sth.test5.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: Bean2 <br>
 * date: 2022/7/25 15:06 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Slf4j
public class Bean4 {
    public Bean4() {
        log.debug("我被 Spring 管理啦");
    }
}
