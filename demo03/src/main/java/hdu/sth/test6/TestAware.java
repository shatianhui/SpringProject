package hdu.sth.test6;

import org.springframework.context.support.GenericApplicationContext;

/**
 * description: TestAware <br>
 * date: 2022/8/6 21:17 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestAware {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("myBean",MyBean.class);

        context.refresh();
        context.close();
    }
}
