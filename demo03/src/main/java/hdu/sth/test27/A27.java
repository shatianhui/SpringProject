package hdu.sth.test27;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.*;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * description: A27 <br>
 * date: 2022/8/6 16:07 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Slf4j
public class A27 {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        // 返回值类型为ModelAndView
        // 返回值为字符串，作为视图名
        // 返回值是自定义对象，且添加了@ModelAttribute注解  默认情况下，视图名就是映射路径的名称
        // 返回值是自定义对象，省略了@ModelAttribute注解
        // 返回值是ResponseEntity 整个响应内容，包含响应状态码，响应头，响应体 不走视图流程
        // 返回值是HttpHeaders,不走视图流程
        // 返回值添加了 @ResponseBody 注解时 , 此时不走视图流程
        test7(context);
    }

    private static void test7(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test7");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); //视图渲染
            }else{
                System.out.println(new String(response.getContentAsByteArray(),StandardCharsets.UTF_8));
            }
        }
    }
    private static void test6(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test6");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); //视图渲染
            }else{
                for (String headerName : response.getHeaderNames()) {
                    System.out.println(headerName+"="+response.getHeader(headerName));
                }
            }
        }
    }
    private static void test5(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test5");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            if (!container.isRequestHandled()) {
                renderView(context, container, webRequest); //视图渲染
            }else{
                System.out.println(new String(response.getContentAsByteArray(),StandardCharsets.UTF_8)); //获取响应内容
            }
        }
    }
    private static void test4(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test4");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test4");  //将请求路径名作为视图名放到request域中，将视图解析的时候获取
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        ServletWebRequest webRequest = new ServletWebRequest(request,new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            renderView(context,container,webRequest); //视图渲染
        }
    }
    private static void test3(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test3");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test3");  //将请求路径名作为视图名放到request域中，将视图解析的时候获取
        UrlPathHelper.defaultInstance.resolveAndCacheLookupPath(request);
        ServletWebRequest webRequest = new ServletWebRequest(request,new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            renderView(context,container,webRequest); //视图渲染
        }
    }
    private static void test2(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test2");
        Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
        ModelAndViewContainer container=new ModelAndViewContainer();
        ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(),new MockHttpServletResponse());
        if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
            composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
            System.out.println(container.getModel());
            System.out.println(container.getViewName());
            renderView(context,container,webRequest); //视图渲染
        }
    }
    private static void test1(AnnotationConfigApplicationContext context) throws Exception {
        Controller controller=new Controller();
        Method method = Controller.class.getDeclaredMethod("test1");
       Object returnValue = method.invoke(controller);  //反射调用方法，获取返回值
       HandlerMethod handlerMethod = new HandlerMethod(controller, method);
       HandlerMethodReturnValueHandlerComposite composite = getReturnValueHandler();
       ModelAndViewContainer container=new ModelAndViewContainer();
       ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest(),new MockHttpServletResponse());
       if (composite.supportsReturnType(handlerMethod.getReturnType())) {  //支持这个返回值处理
           composite.handleReturnValue(returnValue,handlerMethod.getReturnType(),container,webRequest);
           System.out.println(container.getModel());
           System.out.println(container.getViewName());
           renderView(context,container,webRequest); //视图渲染
       }
   }

    public static HandlerMethodReturnValueHandlerComposite getReturnValueHandler() {
        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
        composite.addHandler(new ModelAndViewMethodReturnValueHandler());
        composite.addHandler(new ViewNameMethodReturnValueHandler());
        composite.addHandler(new ServletModelAttributeMethodProcessor(false));
        List list=new ArrayList();
        list.add(new MappingJackson2HttpMessageConverter());
        composite.addHandler(new HttpEntityMethodProcessor(list));
        composite.addHandler(new HttpHeadersReturnValueHandler());
        composite.addHandler(new RequestResponseBodyMethodProcessor(list));
        composite.addHandler(new ServletModelAttributeMethodProcessor(true));
        return composite;
    }

    @SuppressWarnings("all")
    private static void renderView(AnnotationConfigApplicationContext context, ModelAndViewContainer container,
                                   ServletWebRequest webRequest) throws Exception {
        log.debug(">>>>>> 渲染视图");
        FreeMarkerViewResolver resolver = context.getBean(FreeMarkerViewResolver.class);
        String viewName = container.getViewName() != null ? container.getViewName() : new DefaultRequestToViewNameTranslator().getViewName(webRequest.getRequest());
        if (container.getViewName()==null)
            log.debug("没有获取到视图名, 采用默认视图名: {}", viewName);
        // 每次渲染时, 会产生新的视图对象, 它并非被 Spring 所管理, 但确实借助了 Spring 容器来执行初始化
        View view = resolver.resolveViewName(viewName, Locale.getDefault());
        view.render(container.getModel(), webRequest.getRequest(), webRequest.getResponse());
        System.out.println(new String(((MockHttpServletResponse) webRequest.getResponse()).getContentAsByteArray(), StandardCharsets.UTF_8));
    }


    @Slf4j
    static class Controller{
        public ModelAndView test1() {
            log.debug("test1()");
            ModelAndView mav = new ModelAndView("view1");
            mav.addObject("name", "张三");
            return mav;
        }

        public String test2() {   // 返回的字符串就是代表着视图的名字
            log.debug("test2()");
            return "view2";
        }

        @ModelAttribute
        //@RequestMapping("/test3")   // 默认情况下，会将路径的名字当作视图的名字
        public User test3() {
            log.debug("test3()");
            return new User("李四", 20);
        }

        public User test4() {   //省略@ModelAttribute注解
            log.debug("test4()");
            return new User("王五", 30);
        }

        public HttpEntity<User> test5() {  //返回整个响应内容，包括响应的状态码，响应头，响应体
            log.debug("test5()");
            return new HttpEntity<>(new User("赵六", 40));  //由MessageConvertor将对象转化成json数据
        }

        public HttpHeaders test6() {
            log.debug("test6()");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/html");
            return headers;
        }

        @ResponseBody
        public User test7() {
            log.debug("test7()");
            return new User("钱七", 50);
        }
    }

    public static class User{
        private String name;
        private Integer age;

        public User(){}

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
