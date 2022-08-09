package hdu.sth.test31;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: WebConfig <br>
 * date: 2022/8/7 16:43 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig {

    @ControllerAdvice
    static class MyControllerAdvice{
        @ExceptionHandler
        @ResponseBody
        public Map<String,Object> handler(ArithmeticException e){
            Map<String,Object> map=new HashMap<>();
            map.put("err", e.getMessage());
            return map;
        }
    }

    @Bean
    public ExceptionHandlerExceptionResolver resolver(){
        ExceptionHandlerExceptionResolver resolver=new ExceptionHandlerExceptionResolver();
        List list=new ArrayList<>();
        list.add(new MappingJackson2HttpMessageConverter());
        resolver.setMessageConverters(list); //设置消息转换器
        return resolver;
    }
}
