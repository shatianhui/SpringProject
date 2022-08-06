package hdu.sth.test4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * description: Bean1 <br>
 * date: 2022/7/24 15:59 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Slf4j
public class Bean1 {

    private Bean2 bean2;

    @Autowired
    private Bean3 bean3;
    private String java_home;
    // @Autowired可以作用在属性上，但是就打印不了信息了

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.debug("@Autowired 生效：{}", bean2);
        this.bean2 = bean2;
    }

    @Autowired
    public void setJava_home(@Value("${JAVA_HOME}") String java_home) {
        log.debug("@Value 生效：{}", java_home);
        this.java_home = java_home;
    }

    @Resource
    public void setBean3(Bean3 bean3) {
        log.debug("@Resource 生效：{}", bean3);
        this.bean3 = bean3;
    }

    public Bean2 getBean2() {
        return bean2;
    }

    @PostConstruct
    public void init() {
        log.debug("@PostConstruct 生效：{}");
    }

    @PreDestroy
    public void destroy() {
        log.debug("@PreDestroy 生效：{}");
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", java_home='" + java_home + '\'' +
                '}';
    }

}
