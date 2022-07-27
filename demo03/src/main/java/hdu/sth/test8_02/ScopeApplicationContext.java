package hdu.sth.test8_02;

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
        E e = context.getBean(E.class);
        System.out.println(e.getF1());
        System.out.println(e.getF1());
        System.out.println(e.getF1());
        //本应该是三个不同的对象，但结果是三个对象相同
        System.out.println(e.getF1().getClass());
        System.out.println("---------------------");
        System.out.println(e.getF2());
        System.out.println(e.getF2());
        System.out.println(e.getF2());
        System.out.println("--------------");
        System.out.println(e.getF3());
        System.out.println(e.getF3());
        System.out.println("------------");
        System.out.println(e.getF4());
        System.out.println(e.getF4());
    }
}
