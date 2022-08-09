package hdu.sth.test31;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.nio.charset.StandardCharsets;

/**
 * description: A31 <br>
 * date: 2022/8/7 16:43 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A31 {
    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        ExceptionHandlerExceptionResolver resolver = context.getBean(ExceptionHandlerExceptionResolver.class);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        ArithmeticException e = new ArithmeticException("被0整除");
        HandlerMethod handlerMethod = new HandlerMethod(new Controller5(), Controller5.class.getMethod("foo"));
        resolver.resolveException(request, response, handlerMethod, e);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }
    @Controller
    static class Controller5{
        public void foo(){}
    }
}
