package hdu.sth.test43;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("bean1")
@Slf4j
public class Bean1FactoryBean implements FactoryBean<Bean1> {   // 使用Bean1FactoryBean来管理bean1
    // 返回产品类型
    @Override
    public Class<?> getObjectType() {
        return Bean1.class;
    }

    // 产品是单例还是多例
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Bean1 getObject() throws Exception {  //产生产品，创建产品对象
        Bean1 bean1 = new Bean1();
        log.debug("create bean: {}", bean1);
        return bean1;
    }
}
