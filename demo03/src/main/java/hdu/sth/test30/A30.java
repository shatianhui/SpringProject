package hdu.sth.test30;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: A30 <br>
 * date: 2022/8/7 15:35 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A30 {
    public static void main(String[] args) throws NoSuchMethodException {
        // 测试json
        // 测试mav
        // 处理嵌套异常
        // 异常处理方法的参数解析  ExceptionHandlerExceptionResolver在创建时，调用初始化方法，添加了参数解析器和返回值处理器 ，所以在方法中才可以获得参数
        test4();

    }

    private static void test4() throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver=new ExceptionHandlerExceptionResolver();  //异常解析器
        HandlerMethod method = new HandlerMethod(new Controller4(), Controller4.class.getMethod("foo"));
        List list=new ArrayList<>();
        list.add(new MappingJackson2HttpMessageConverter());
        resolver.setMessageConverters(list); //设置消息转换器
        resolver.afterPropertiesSet(); //添加参数解析器以及返回值处理器
        MockHttpServletRequest request=new MockHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        ArithmeticException exception = new ArithmeticException("被0除");
        resolver.resolveException(request, response, method, exception);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }

    private static void test3() throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver=new ExceptionHandlerExceptionResolver();  //异常解析器
        HandlerMethod method = new HandlerMethod(new Controller3(), Controller3.class.getMethod("foo"));
        List list=new ArrayList<>();
        list.add(new MappingJackson2HttpMessageConverter());
        resolver.setMessageConverters(list); //设置消息转换器
        resolver.afterPropertiesSet(); //添加参数解析器以及返回值处理器
        MockHttpServletRequest request=new MockHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        Exception exception = new Exception("e1", new RuntimeException("e2", new IOException("e3")));
        resolver.resolveException(request, response, method, exception);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }

    private static void test2() throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver=new ExceptionHandlerExceptionResolver();  //异常解析器
        HandlerMethod method = new HandlerMethod(new Controller2(), Controller2.class.getMethod("foo"));
        List list=new ArrayList<>();
        list.add(new MappingJackson2HttpMessageConverter());
        resolver.setMessageConverters(list); //设置消息转换器
        resolver.afterPropertiesSet(); //添加参数解析器以及返回值处理器
        MockHttpServletRequest request=new MockHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        ArithmeticException exception = new ArithmeticException("被0除");
        ModelAndView modelAndView = resolver.resolveException(request, response, method, exception);
        System.out.println(modelAndView.getModel());
        System.out.println(modelAndView.getViewName());
    }
    private static void test1() throws NoSuchMethodException {
        ExceptionHandlerExceptionResolver resolver=new ExceptionHandlerExceptionResolver();  //异常解析器
        HandlerMethod method = new HandlerMethod(new Controller1(), Controller1.class.getMethod("foo"));
        List list=new ArrayList<>();
        list.add(new MappingJackson2HttpMessageConverter());
        resolver.setMessageConverters(list); //设置消息转换器
        resolver.afterPropertiesSet(); //添加参数解析器以及返回值处理器
        MockHttpServletRequest request=new MockHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        ArithmeticException exception = new ArithmeticException("被0除");
        resolver.resolveException(request, response, method, exception);
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }
    @Controller
    static class Controller1{
        public void foo(){}
        @ExceptionHandler
        @ResponseBody
        public Map<String,Object> handler(ArithmeticException e){
            Map<String,Object> map=new HashMap<>();
            map.put("err", e.getMessage());
            return map;
        }
    }

    @Controller
    static class Controller2{
        public void foo(){}
        @ExceptionHandler
        public ModelAndView handler(ArithmeticException e){
            Map<String,Object> map=new HashMap<>();
            map.put("err", e.getMessage());
            return new ModelAndView("test2",map);
        }
    }

    @Controller
    static class Controller3{
        public void foo(){}
        @ExceptionHandler
        @ResponseBody
        public Map<String,Object> handler(IOException e){
            Map<String,Object> map=new HashMap<>();
            map.put("err", e.getMessage());
            return map;
        }
    }

    @Controller
    static class Controller4{
        public void foo(){}
        @ExceptionHandler
        @ResponseBody
        public Map<String,Object> handler(ArithmeticException e, HttpServletRequest request){
            System.out.println(request);
            Map<String,Object> map=new HashMap<>();
            map.put("err", e.getMessage());
            return map;
        }
    }
}
