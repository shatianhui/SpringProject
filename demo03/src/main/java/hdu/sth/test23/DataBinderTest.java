package hdu.sth.test23;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;

import java.util.Date;

/**
 * description: DateBinderTest <br>
 * date: 2022/8/3 17:21 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class DataBinderTest {
    public static void main(String[] args) {
        MyBean target=new MyBean();
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.initDirectFieldAccess();  //通过属性
        MutablePropertyValues pvs=new MutablePropertyValues();
        pvs.addPropertyValue("a", "22");
        pvs.addPropertyValue("b", "22.8");
        pvs.addPropertyValue("c", "2022/08/03");
        dataBinder.bind(pvs); //默认通过set方法
        System.out.println(target);
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
