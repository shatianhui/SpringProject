package hdu.sth.test20;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.Yaml;

/**
 * description: Controller1 <br>
 * date: 2022/8/2 15:18 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Controller
@Slf4j
public class Controller1 {
    @GetMapping("/test1")
    public ModelAndView test1() throws Exception {
        log.debug("test1()");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    @PutMapping("/test3")
    public ModelAndView test3(@Token String token) {
        log.debug("test3({})", token);
        return null;
    }

    @RequestMapping("/test4")
//    @ResponseBody
    @Yml
    public User test4() {
        log.debug("test4");
        return new User("张三", 18);
    }

    public static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public static void main(String[] args) {
            User user=new User("张三",18);
            String s = new Yaml().dump(user);
            System.out.println(s); //!!hdu.sth.test20.Controller1$User {age: 18, name: 张三}
        }
    }
}
