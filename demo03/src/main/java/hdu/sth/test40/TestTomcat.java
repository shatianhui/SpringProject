package hdu.sth.test40;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * description: TestTomcat <br>
 * date: 2022/8/14 20:48 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TestTomcat {
    public static void main(String[] args) throws IOException, LifecycleException {
        // 1.创建Tomcat
        Tomcat tomcat=new Tomcat();
        tomcat.setBaseDir("tomcat");//设置基础目录

        // 2.创建项目文件夹 docBase（项目磁盘路径）
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();

        // 3.创建Tomcat项目（Context）    contextPath表示虚拟目录
        Context context = tomcat.addContext("", docBase.getAbsolutePath());//虚拟目录设置为/

        // 创建Spring容器
        WebApplicationContext applicationContext = getApplicationContext();

        // 4.编程添加Servlet
        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
                servletContext.addServlet("helloServlet", HelloServlet.class).addMapping("/hello");

                // 将Spring容器中的DispatcherServlet添加到应用中
                /*DispatcherServlet dispatcherServlet = applicationContext.getBean(DispatcherServlet.class);
                servletContext.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/");*/
                for (ServletRegistrationBean registrationBean : applicationContext.getBeansOfType(ServletRegistrationBean.class).values()) {
                    registrationBean.onStartup(servletContext); //将Spring容器中的Servlet添加到应用中
                }
            }
        }, Collections.emptySet());

        // 5.启动Tomcat
        tomcat.start();

        // 6.创建连接器Connector 设置监听端口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }

    public static WebApplicationContext getApplicationContext(){
        // 创建Spring容器，不使用内嵌Tomcat的那个实现类
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Config.class);
        context.refresh();
        return context;
    }

    @Configuration
    public static class Config{
        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet) { //这个注册bean就是将Servlet添加到context(Tomcat中)
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean
        // 这个例子中必须为 DispatcherServlet 提供 AnnotationConfigWebApplicationContext, 否则会选择 XmlWebApplicationContext 实现
        public DispatcherServlet dispatcherServlet(WebApplicationContext applicationContext) {
            return new DispatcherServlet(applicationContext);
        }

        @Bean
        public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
            RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
            List list=new ArrayList<>();
            list.add(new MappingJackson2HttpMessageConverter());
            handlerAdapter.setMessageConverters(list);
            return handlerAdapter;
        }

        @RestController
        static class MyController {
            @GetMapping("hello2")
            public Map<String,Object> hello() {
                Map<String,Object> map=new HashMap<>();
                map.put("hello2", "hello2, spring!");
                return map;
            }
        }
    }
}
