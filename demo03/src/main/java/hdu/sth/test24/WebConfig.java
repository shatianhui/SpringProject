package hdu.sth.test24;

import hdu.sth.test23.MyDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * description: WebConfig <br>
 * date: 2022/8/5 16:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig {
    @ControllerAdvice  //类注解，对所有的控制器做增强功能
    static class MyControllerAdvice{
        @InitBinder  //添加自定义转换器
        public void binder3(WebDataBinder dataBinder){
            dataBinder.addCustomFormatter(new MyDateFormatter("binder3转换器"));
        }
    }

    @Controller
    static class MyController1{
        @InitBinder
        public void binder1(WebDataBinder dataBinder){
            dataBinder.addCustomFormatter(new MyDateFormatter("binder1转换器"));
        }
        public void foo(){}
    }

    @Controller
    static class MyController2{
        @InitBinder
        public void binder21(WebDataBinder dataBinder){
            dataBinder.addCustomFormatter(new MyDateFormatter("binder21转换器"));
        }

        @InitBinder
        public void binder22(WebDataBinder dataBinder){
            dataBinder.addCustomFormatter(new MyDateFormatter("binder22转换器"));
        }

        public void bar(){}
    }
}
