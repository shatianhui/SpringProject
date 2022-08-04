package hdu.sth.test23;

import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

/**
 * description: BeanWrapperTest <br>
 * date: 2022/8/3 17:07 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class BeanWrapperTest {
    public static void main(String[] args) {
        //内部通过反射的set方法进行绑定
        MyBean target=new MyBean();
        BeanWrapperImpl beanWrapper=new BeanWrapperImpl(target);
        beanWrapper.setPropertyValue("a","12");
        beanWrapper.setPropertyValue("b","12.8");
        beanWrapper.setPropertyValue("c","2022/08/03");
        System.out.println(target);  //注释掉set方法就绑定不上了
    }

    static class MyBean{
        private Integer a;
        private Double b;
        private Date c;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public Double getB() {
            return b;
        }

        public void setB(Double b) {
            this.b = b;
        }

        public Date getC() {
            return c;
        }

        public void setC(Date c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }
}
