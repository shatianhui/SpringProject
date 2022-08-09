package hdu.sth.test29;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * description: WebConfig <br>
 * date: 2022/8/7 11:34 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig {


    @ControllerAdvice
    static class MyControllerAdvice implements ResponseBodyAdvice<Object> { //对响应体做增强
        // 满足条件做转换
        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            // 如果方法上有@ResponseBody或者该类上有@ResponseBody才做转换
            if (returnType.getMethodAnnotation(ResponseBody.class)!=null ||
                    AnnotationUtils.findAnnotation(returnType.getContainingClass(),ResponseBody.class)!=null){  //注意组合注解@RestController
                return true;
            }
            return false;
        }

        //将不是Result类型统一成Result类型
        //第一个参数就是请求体数据
        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if (body instanceof Result){
                return body;
            }else {
                return Result.ok(body);
            }
        }

    }


//    @Controller
//    @ResponseBody
    @RestController
    static class MyController{
        //@ResponseBody   //需求是不是返回User的json数据，而是统一返回Result类型
        public User user(){
            return new User("张三",22);
        }
    }

    public static class User{
        private String name;
        private Integer age;
        public User(){}

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
