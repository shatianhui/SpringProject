package hdu.sth.test32;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: WebConfig <br>
 * date: 2022/8/7 17:01 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Configuration
public class WebConfig {
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
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){  // 修改了 Tomcat 服务器默认错误地址
        ErrorPageRegistrar errorPageRegistrar=new ErrorPageRegistrar() {   // 出现错误，会使用请求转发forward 跳转到error 地址
            @Override
            public void registerErrorPages(ErrorPageRegistry servletWebServerFactory) {
                servletWebServerFactory.addErrorPages(new ErrorPage("/error"));
            }
        };
        return errorPageRegistrar;
    }

    @Bean
    public ErrorPageRegistrarBeanPostProcessor errorPageRegistrarBeanPostProcessor(){
        return new ErrorPageRegistrarBeanPostProcessor();
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {  //存储请求路径和控制器方法的对应关系
        return new RequestMappingHandlerMapping();
    }

    @Bean // 注意默认的 RequestMappingHandlerAdapter 不会带 jackson 转换器
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {  //执行控制器方法
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        List list=new ArrayList();
        list.add(new MappingJackson2HttpMessageConverter());
        handlerAdapter.setMessageConverters(list);
        return handlerAdapter;
    }

    @Bean
    public BasicErrorController basicErrorController(){
        ErrorProperties properties=new ErrorProperties(); // errorProperties是读取配置文件中的键值对
        properties.setIncludeException(true); // 响应中是否包含异常信息
        BasicErrorController basicErrorController=new BasicErrorController(new DefaultErrorAttributes(),properties);
        return  basicErrorController;
    }

    @Bean
    public View error(){
        return new View() {
            @Override
            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                System.out.println(model);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("<h3>服务器错误</h3>");
            }
        };
    }

    @Bean
    public ViewResolver viewResolver(){ // 视图解析器作用：根据视图名找到视图对象  比如根据视图名error, 在容器中寻找名字为error的bean
        return new BeanNameViewResolver();
    }


    @Controller
    public static class MyController {
        @RequestMapping("test")
        public ModelAndView test() {
            int i = 1 / 0;
            return null;
        }

//        @RequestMapping("/error")
//        @ResponseBody
//        public Map<String,Object> error(HttpServletRequest request){  //异常信息会被Tomcat放在request作用域中
//            Map<String,Object> map=new HashMap<>();
//            Throwable e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
//            map.put("err", e.getMessage());
//            return map;
//        }
    }
}
