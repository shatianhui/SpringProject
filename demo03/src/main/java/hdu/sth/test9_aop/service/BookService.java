package hdu.sth.test9_aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * description: BookService <br>
 * date: 2022/7/28 10:07 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class BookService {
    public void save(){
      log.debug("book save....");
      get();  //使用aop代理方式，get()会得到增强吗  答案是不会
    }

    public void get(){
        log.debug("book get....");
    }
}
