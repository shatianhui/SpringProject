package hdu.sth.test2_01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: Config <br>
 * date: 2022/7/26 16:51 <br>
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
    public Bean2 bean2() {
        return new Bean2();
    }

}

