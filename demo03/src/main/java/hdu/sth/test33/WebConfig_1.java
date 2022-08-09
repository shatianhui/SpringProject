package hdu.sth.test33;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description: WebConfig <br>
 * 自定义实现BeanNameUrlHandlerMapping及 SimpleControllerHandlerAdapter
 * date: 2022/8/2 20:18 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig_1 {
    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public DispatcherServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        return registrationBean;
    }

    @Component
    public static class MyHandlerMapping implements HandlerMapping{  // 寻找容器中所有以/开头的控制器
        @Autowired
        private ApplicationContext context;
        private Map<String,Controller> controller;

        @PostConstruct
        public void init(){  //先进行依赖注入（所以，context不为空），后进行初始化
            Map<String, Controller> beansOfType = context.getBeansOfType(Controller.class);  //查找容器中所有类型为Controller的bean
            //过滤出名字以斜杠开头的控制器
            controller=beansOfType.entrySet().stream().filter(e -> e.getKey().startsWith("/")).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            //System.out.println(controller);
        }

        @Override
        public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
            String path=request.getRequestURI();
            Controller controller = this.controller.get(path);
            if (controller!=null){
                return new HandlerExecutionChain(controller);
            }else{
                return null;
            }

        }
    }

   @Component
   public static class MyHandlerAdapter implements HandlerAdapter{

       @Override
       public boolean supports(Object handler) {
           return handler instanceof Controller; //控制器是否是Controller类型
       }

       @Override
       public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           if (handler instanceof Controller){
               return ((Controller) handler).handleRequest(request, response);  // 调用Controller中的handleRequest()方法
           }
           return null;
       }

       @Override
       public long getLastModified(HttpServletRequest request, Object handler) {
           return -1;
       }
   }

    @Component("/c1")
    public static class Controller1 implements Controller {
        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().print("This is c1");
            return null;
        }
    }
    @Component("c2")
    public static class Controller2 implements Controller {
        @Override
        public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().print("This is c2");
            return null;
        }
    }

    @Bean("/c3")
    public Controller controller3(){
        return (request, response) -> {
            response.getWriter().print("This is c3");
            return null;
        };
    }
}
