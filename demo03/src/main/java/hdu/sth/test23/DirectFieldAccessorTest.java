package hdu.sth.test23;

import org.springframework.beans.DirectFieldAccessor;

import java.util.Date;

/**
 * description: DirectFieldAccessorTest <br>
 * date: 2022/8/3 17:17 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class DirectFieldAccessorTest {
    public static void main(String[] args) {
        MyBean target=new MyBean();
        DirectFieldAccessor directFieldAccessor=new DirectFieldAccessor(target);
        directFieldAccessor.setPropertyValue("a","18");
        directFieldAccessor.setPropertyValue("b","18.8");
        directFieldAccessor.setPropertyValue("c","2022/08/03");
        System.out.println(target);  // 反射通过属性来绑定的
    }

    static class MyBean{
        private Integer a;
        private Double b;
        private Date c;

//        public Integer getA() {
//            return a;
//        }
//
//        public void setA(Integer a) {
//            this.a = a;
//        }
//
//        public Double getB() {
//            return b;
//        }
//
//        public void setB(Double b) {
//            this.b = b;
//        }
//
//        public Date getC() {
//            return c;
//        }
//
//        public void setC(Date c) {
//            this.c = c;
//        }


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
