package hdu.sth.test2_02;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * description: WebConfig <br>
 * date: 2022/7/26 17:17 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@Configuration
public class WebConfig {
    @Bean
    // 1. WebServer工厂   Tomcat WebServer服务器   内嵌tomcat
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    // 2. web项目必备的DispatcherServlet
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    // 3. 将DispatcherServlet注册到WebServer上
    public DispatcherServletRegistrationBean
    dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
        return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        // 表示所有的请求都要交给dispatcherServlet来处理,再由DispatcherServlet分发给其他控制器
    }

    @Bean("/hello")  //访问路径
    public Controller controller1() {
        return new Controller() {
            @Override
            public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
                response.getWriter().write("hello spring");
                return null;
            }
        };
    }
}

