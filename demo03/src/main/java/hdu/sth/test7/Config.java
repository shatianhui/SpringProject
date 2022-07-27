package hdu.sth.test7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: Config <br>
 * date: 2022/7/27 11:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class Config {
    @Bean(initMethod = "init3")
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean(destroyMethod = "destroy3")
    public Bean2 bean2() {
        return new Bean2();
    }
}