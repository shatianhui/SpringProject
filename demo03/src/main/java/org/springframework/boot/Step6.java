package org.springframework.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * description: Step6 <br>
 * date: 2022/8/12 21:40 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Step6 {
    public static void main(String[] args) throws IOException {
        SpringApplication spring = new SpringApplication();
        ApplicationEnvironment env=new ApplicationEnvironment();
        env.getPropertySources().addLast(new ResourcePropertySource("classpath:step4.properties"));
        // 例1，将properties和Java对象进行绑定
//        User user = Binder.get(env).bind("user", User.class).get();
//        System.out.println(user);
        //例2：对已有的对象做绑定
//        User user = new User();
//        Binder.get(env).bind("user", Bindable.ofInstance(user));
//        System.out.println(user);
        // 将spring.main的键值与SpringApplication中的属性进行绑定
        System.out.println(spring);//绑定前
        env.getPropertySources().addLast(new ResourcePropertySource("classpath:step6.properties"));
        Binder.get(env).bind("spring.main", Bindable.ofInstance(spring));
        System.out.println(spring);//绑定后
    }

    //@ConfigurationProperties(prefix = "user")
    static class User{
        private String firstName;
        private String middleName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", middleName='" + middleName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
}
