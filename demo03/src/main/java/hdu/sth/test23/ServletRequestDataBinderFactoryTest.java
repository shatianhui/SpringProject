package hdu.sth.test23;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: ServletRequestDataBinderFactoryTest <br>
 * date: 2022/8/4 10:06 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class ServletRequestDataBinderFactoryTest {
    public static void main(String[] args) throws Exception {
        MockHttpServletRequest request=new MockHttpServletRequest();
        request.setParameter("birthday","2022|08|04");
        request.setParameter("address.name","杭州");
        User user=new User();
        // ServletRequestDataBinder dataBinder=new ServletRequestDataBinder(user);
        // 1. 用工厂创建绑定器  无扩展功能
        //ServletRequestDataBinderFactory factory=new ServletRequestDataBinderFactory(null,null);
        // 2. 用@InitBinder转换   PropertyEditorRegistry  PropertyEditor
//        InvocableHandlerMethod method = new InvocableHandlerMethod(new Controller(), Controller.class.getMethod("aaa", WebDataBinder.class));
//        List list=new ArrayList<>();
//        list.add(method);
//        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(list, null);
        // 3.使用ConversionService转换  ConversionService Formatter
//        FormattingConversionService conversionService=new FormattingConversionService();
//        conversionService.addFormatter(new MyDateFormatter("使用ConversionService扩展转换功能"));
//        ConfigurableWebBindingInitializer initializer=new ConfigurableWebBindingInitializer();
//        initializer.setConversionService(conversionService);
//        ServletRequestDataBinderFactory factory=new ServletRequestDataBinderFactory(null,initializer);
        // 4.同时使用 @InitBinder 及 ConversionService   使用的是@InitBinder
//        FormattingConversionService conversionService=new FormattingConversionService();
//        conversionService.addFormatter(new MyDateFormatter("使用ConversionService扩展转换功能"));
//        ConfigurableWebBindingInitializer initializer=new ConfigurableWebBindingInitializer();
//        initializer.setConversionService(conversionService);
//        InvocableHandlerMethod method = new InvocableHandlerMethod(new Controller(), Controller.class.getMethod("aaa", WebDataBinder.class));
//        List list=new ArrayList<>();
//        list.add(method);
//        ServletRequestDataBinderFactory factory=new ServletRequestDataBinderFactory(list,initializer);
        // 使用默认的ConversionService  就不需要再自定义一个转换器   配合@DateTimeFormat使用
        // 对于Springboot项目  使用 ApplicationConversionService
        DefaultFormattingConversionService conversionService=new DefaultFormattingConversionService();
        ConfigurableWebBindingInitializer initializer=new ConfigurableWebBindingInitializer();
        initializer.setConversionService(conversionService);
        ServletRequestDataBinderFactory factory=new ServletRequestDataBinderFactory(null,initializer);
        // 传入三个参数  ①封装好的request对象  ②目标对象   ③bean的名字
        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), user, "user");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(user);
    }

    static class Controller{
        @InitBinder
        public void aaa(WebDataBinder dataBinder){
            // 扩展dataBinder转换器
            dataBinder.addCustomFormatter(new MyDateFormatter("用@InitBinder方式扩展"));
        }
    }

    static class User{
        @DateTimeFormat(pattern = "yyyy|MM|dd")
        private Date birthday;
        private Address address;

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "birthday=" + birthday +
                    ", address=" + address +
                    '}';
        }
    }

    static class Address{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
