package hdu.sth.test25;

import hdu.sth.test20.Controller1;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * description: WebConfig <br>
 * date: 2022/8/6 10:33 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig {
    @ControllerAdvice
    static class MyControllerAdvice{
        @ModelAttribute("a")
        public String aa(){
            return "aa";
        }
    }

    @Controller
    static class Controller1{

        @ModelAttribute("b")
        public String bb(){
            return "bb";
        }

        @ResponseStatus(HttpStatus.OK)  //这个注解只是为了做测试用，不考虑处理返回值结果
        public ModelAndView foo(User user){
            System.out.println("foo()....");
            return null;
        }
    }

    static class User{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
