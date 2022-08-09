package hdu.sth.test35;

import org.springframework.boot.autoconfigure.web.servlet.WebConfig;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

/**
 * description: A33 <br>
 * date: 2022/8/8 20:18 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A35 {
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context=new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

    }
}
