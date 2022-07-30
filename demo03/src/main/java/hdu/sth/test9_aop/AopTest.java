package hdu.sth.test9_aop;

import hdu.sth.Demo03Application;
import hdu.sth.test9_aop.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * description: AopTest <br>
 * date: 2022/7/28 10:10 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@SpringBootApplication
public class AopTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AopTest.class, args);
        BookService bookService = context.getBean(BookService.class);
        bookService.save();
    }
}
