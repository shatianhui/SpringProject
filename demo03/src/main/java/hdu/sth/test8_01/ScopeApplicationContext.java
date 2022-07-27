package hdu.sth.test8_01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * description: ScopeApplicationContext <br>
 * date: 2022/7/27 16:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
@SpringBootApplication
public class ScopeApplicationContext {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ScopeApplicationContext.class);
    }
}
