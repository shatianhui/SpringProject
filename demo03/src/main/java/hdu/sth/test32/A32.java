package hdu.sth.test32;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * description: A32 <br>
 * date: 2022/8/7 17:01 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A32 {
    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context=new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        handlerMapping.getHandlerMethods().forEach((k,v)-> System.out.println("请求路径:"+k+"方法信息："+v));
    }
}
