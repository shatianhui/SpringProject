package hdu.sth.test47;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * description: A47_1 <br>
 * date: 2022/8/20 9:42 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class A47_1 {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A47_1.class);
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        // 1.根据成员变量类型进行注入
        DependencyDescriptor dd1=new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
        System.out.println(beanFactory.doResolveDependency(dd1, "bean1", null, null));
        // 2.根据方法参数进行注入
        DependencyDescriptor dd2=new DependencyDescriptor(new MethodParameter(Bean1.class.getDeclaredMethod("setBean2", Bean2.class),0),false);
        System.out.println(beanFactory.doResolveDependency(dd2, "bean1", null, null));
        //3.结果包装为Optional<Bean2>
        DependencyDescriptor dd3=new DependencyDescriptor(Bean1.class.getDeclaredField("bean3"),false);
//        System.out.println(dd3.getDependencyType());
//        dd3.increaseNestingLevel();
//        System.out.println(dd3.getDependencyType());
        if (dd3.getDependencyType()==Optional.class){
            dd3.increaseNestingLevel(); //进入新的一层
            Object result = beanFactory.doResolveDependency(dd3, "bean1", null, null);
            //将结果封装成Optional
            Optional<Object> result1 = Optional.ofNullable(result);
            System.out.println(result1);
        }
        //4.结果封装为ObjectFactory  ObjectProvider
        DependencyDescriptor dd4=new DependencyDescriptor(Bean1.class.getDeclaredField("bean4"),false);
        if (dd4.getDependencyType()==ObjectFactory.class){
            dd4.increaseNestingLevel();

            // ObjectFactory调用getObject()时才返回产品对象
            ObjectFactory objectFactory=new ObjectFactory() {
                @Override
                public Object getObject() throws BeansException {
                    return beanFactory.doResolveDependency(dd4, "bean1", null, null);
                }
            };
            System.out.println(objectFactory.getObject());
        }

        //5.对@Lazy的处理，作用：创建一个代理对象
        DependencyDescriptor dd5=new DependencyDescriptor(Bean1.class.getDeclaredField("bean2"),false);
        // 作用1：解析@Value注解   作用2：解析@Lazy注解，有@Lazy注解 产生代理对象，而不是直接注入目标
        ContextAnnotationAutowireCandidateResolver resolver=new ContextAnnotationAutowireCandidateResolver();
        resolver.setBeanFactory(beanFactory);
        Object proxy = resolver.getLazyResolutionProxyIfNecessary(dd5, "bean1");
        System.out.println(proxy);
        System.out.println(proxy.getClass());
    }

    static class Bean1{
        @Autowired
        @Lazy
        private Bean2 bean2;

        @Autowired
        public void setBean2(Bean2 bean2){
            this.bean2=bean2;
        }

        @Autowired
        private Optional<Bean2> bean3;

        @Autowired
        private ObjectFactory<Bean2> bean4;
    }

    @Component()
    static class Bean2{
    }
}
