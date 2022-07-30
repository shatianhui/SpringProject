package hdu.sth.test11;

import lombok.extern.slf4j.Slf4j;

/**
 * description: Target <br>
 * date: 2022/7/28 15:14 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Slf4j
public class Target implements Foo{
    public void foo() {
        log.debug("target foo");
    }
}
