package hdu.sth.test2_02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: Config <br>
 * date: 2022/7/26 17:16 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class Config {
    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public Bean2 bean2(Bean1 bean1) {
        Bean2 bean2 = new Bean2();
        bean2.setBean1(bean1);
        return bean2;
    }
}


