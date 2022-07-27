package hdu.sth.test8_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * description: F1 <br>
 * date: 2022/7/27 20:01 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Component
@Scope(scopeName = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class F2 {
    public F2() {
        log.info("F2()");
    }
}