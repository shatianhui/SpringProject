package hdu.sth.test9_ajc;

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

public class AjcTest {
    public static void main(String[] args) {
        MyService service = new MyService();
        System.out.println(service.getClass());
        service.foo();
    }
}
