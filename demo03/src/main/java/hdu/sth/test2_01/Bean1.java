package hdu.sth.test2_01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * description: Bean1 <br>
 * date: 2022/7/26 16:50 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class Bean1 {
    @Autowired
    private Bean2 bean2;

//    @Autowired
//    private Inter inter;  //这样写一定会注入失败，因为Iter类型的bean不止一个

    @Autowired
    @Resource(name = "bean4")  //注入bean4
    private Inter bean3;   //两个都加，注入bean3,跟后处理器的顺序有关

    public Inter getBean3() {
        return bean3;
    }

    public Bean2 getBean2() {
        return bean2;
    }

    public Bean1() {
        log.debug("构造 Bean1()");
    }
}
