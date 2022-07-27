package hdu.sth.test6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

/**
 * description: MyConfig2 <br>
 * date: 2022/7/27 11:10 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class MyConfig2 implements ApplicationContextAware, InitializingBean {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.debug("注入 ApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("初始化");
    }

    @Bean
    public BeanFactoryPostProcessor processor1() {
        return beanFactory -> {
            log.debug("执行 processor1");
        };
    }
}
