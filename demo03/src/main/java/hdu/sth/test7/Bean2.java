package hdu.sth.test7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PreDestroy;

/**
 * description: Bean2 <br>
 * date: 2022/7/27 11:44 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class Bean2 implements DisposableBean {
    @PreDestroy
    public void destroy1() {
        log.debug("销毁1，@PreDestory");
    }

    @Override
    public void destroy() throws Exception {
        log.debug("销毁2，DisposableBean接口");
    }

    public void destroy3() {
        log.debug("销毁3，@Bean的destroyMethod");
    }
}
