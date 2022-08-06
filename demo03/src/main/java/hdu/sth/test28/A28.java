package hdu.sth.test28;

/**
 * description: A28 <br>
 * date: 2022/8/6 19:45 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A28 {
    public static void main(String[] args) {

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
    }
}
