package hdu.sth.test23;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;

import java.util.Date;

/**
 * description: ServletRequestDataBinderTest <br>
 * date: 2022/8/3 19:53 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class ServletRequestDataBinderTest {
    public static void main(String[] args) {
        // web环境下的数据绑定
        MyBean target=new MyBean();
        ServletRequestDataBinder dataBinder=new ServletRequestDataBinder(target);
        MockHttpServletRequest request=new MockHttpServletRequest();
        request.setParameter("a","15");
        request.setParameter("b","15.8");
        request.setParameter("c","2022/08/01");
        dataBinder.bind(request);
        System.out.println(target);
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
