package hdu.sth.test20;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.SpringProperties;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * description: WebConfig <br>
 * date: 2022/8/2 10:44 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")  //加载配置文件
// Spring容器创建这两个对象，并且与配置文件中的键值对进行绑定
@EnableConfigurationProperties({WebMvcProperties.class, ServerProperties.class})
public class WebConfig {
    // 内嵌Web容器工厂
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties){
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }

    // 创建DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    // 注册DispatcherServlet   因为DispatcherServlet是在Tomcat中运行的
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet,WebMvcProperties webMvcProperties){
        //表示所有请求都交由DispatcherServlet处理，然后由它进行转发
        DispatcherServletRegistrationBean registrationBean=new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        //写代码要避免硬编码，由配置文件进行读取
        registrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registrationBean;
    }

    // 将RequestMappingHandlerMapping 加入到spring容器中
    @Bean
    public RequestMappingHandlerMapping handlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public MyRequestMappingHandlerAdapter handlerAdapter(){
        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();
        // 添加自定义参数解析器
        List list=new ArrayList();
        list.add(new TokenArgumentResolver());
        handlerAdapter.setCustomArgumentResolvers(list);
        //添加自定义的返回值处理器
        List list1=new ArrayList();
        list1.add(new YmlReturnValueHandler());
        handlerAdapter.setCustomReturnValueHandlers(list1);
        return handlerAdapter;
    }
}
