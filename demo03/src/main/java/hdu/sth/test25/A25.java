package hdu.sth.test25;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * description: A25 <br>
 * date: 2022/8/6 10:41 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A25 {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        // 通过ServletInvocableHandlerMethod将组件整合在一起，完成控制器方法的调用
        ServletInvocableHandlerMethod handlerMethod=
                new ServletInvocableHandlerMethod(new WebConfig.Controller1(), WebConfig.Controller1.class.getMethod("foo", WebConfig.User.class));
        MockHttpServletRequest request=new MockHttpServletRequest();
        request.setParameter("name","张三");

        //负责解析@ModelAttribute
        RequestMappingHandlerAdapter handlerAdapter=new RequestMappingHandlerAdapter();
        //手工调用初始化
        handlerAdapter.setApplicationContext(context);
        handlerAdapter.afterPropertiesSet();


        WebDataBinderFactory binderFactory=new ServletRequestDataBinderFactory(null,null); //框架内部是自己读取@InitBinder注解的方法，添加自定义转换器
        handlerMethod.setDataBinderFactory(binderFactory);

        ParameterNameDiscoverer discoverer=new DefaultParameterNameDiscoverer(); //处理参数的名字
        handlerMethod.setParameterNameDiscoverer(discoverer);
        handlerMethod.setHandlerMethodArgumentResolvers(getArgumentResolvers(context));
        ModelAndViewContainer container=new ModelAndViewContainer();  //存放模型数据  ，比如参数解析器解析@ModelAttribute时就会把参数解析结果放到这个容器中，对象类型第一个字母小写作为名字


        //反射获得RequestMappingHandlerAdapter中getModelFactory方法 并调用
        Method getModelFactory = RequestMappingHandlerAdapter.class.getDeclaredMethod("getModelFactory", HandlerMethod.class, WebDataBinderFactory.class);
        getModelFactory.setAccessible(true);
        ModelFactory modelFactory = (ModelFactory) getModelFactory.invoke(handlerAdapter, handlerMethod, binderFactory);
        modelFactory.initModel(new ServletWebRequest(request),container,handlerMethod); //在ModelAndViewContainer中添加模型数据

        handlerMethod.invokeAndHandle(new ServletWebRequest(request),container);
        System.out.println("ModelAndViewContainer中内容"+container.getModel());
        context.close();

    }
    public static HandlerMethodArgumentResolverComposite getArgumentResolvers(AnnotationConfigApplicationContext context) {
        HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        composite.addResolvers(
                new RequestParamMethodArgumentResolver(context.getDefaultListableBeanFactory(), false),
                new PathVariableMethodArgumentResolver(),
                new RequestHeaderMethodArgumentResolver(context.getDefaultListableBeanFactory()),
                new ServletCookieValueMethodArgumentResolver(context.getDefaultListableBeanFactory()),
                new ExpressionValueMethodArgumentResolver(context.getDefaultListableBeanFactory()),
                new ServletRequestMethodArgumentResolver(),
                new ServletModelAttributeMethodProcessor(false),
                //new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter())),  //List.of Java9
                new ServletModelAttributeMethodProcessor(true),
                new RequestParamMethodArgumentResolver(context.getDefaultListableBeanFactory(), true)
        );
        return composite;
    }
}
