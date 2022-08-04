package hdu.sth.test21;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description: A21 <br>
 * date: 2022/8/2 19:45 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A21 {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(WebConfig.class);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 准备测试 Request
        HttpServletRequest request = mockRequest();

        //对象绑定和类型转换
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, null);

        // 创建HandlerMethod对象
        HandlerMethod handlerMethod=new HandlerMethod(new Controller(),Controller.class.getDeclaredMethod("test", String.class, String.class, int.class, String.class, MultipartFile.class, int.class, String.class, String.class, String.class, HttpServletRequest.class, User.class, User.class, User.class));

        //ModelAndViewContainer 存储中间Model结果
        ModelAndViewContainer modelAndViewContainer=new ModelAndViewContainer();

        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
            // RequestParamMapMethodArgumentResolver专门解析@RequestParam注解  false表示不可以解析省略@RequestParam注解
            // 为了获取${}非请求的值，需要传入beanFactory
            //RequestParamMethodArgumentResolver requestParamMethodArgumentResolver=new RequestParamMethodArgumentResolver(beanFactory,false);
            // 多个解析器组合
            HandlerMethodArgumentResolverComposite composite=new HandlerMethodArgumentResolverComposite();
            List converters=new ArrayList();
            converters.add(new MappingJackson2HttpMessageConverter());  //json转换器
            composite.addResolvers(
                    new RequestParamMethodArgumentResolver(beanFactory,false),
                    new PathVariableMethodArgumentResolver(),
                    new RequestHeaderMethodArgumentResolver(beanFactory),
                    new ServletCookieValueMethodArgumentResolver(beanFactory),
                    new ExpressionValueMethodArgumentResolver(beanFactory),
                    new ServletRequestMethodArgumentResolver(),
                    new ServletModelAttributeMethodProcessor(false),  //必须添加@ModelAttribute注解
                    new RequestResponseBodyMethodProcessor(converters),
                    new ServletModelAttributeMethodProcessor(true),  //解析未添加@ModelAttribute注解
                    new RequestParamMethodArgumentResolver(beanFactory,true)  //解析未添加@RequestParam注解
            );
            parameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());  //就是为了显示参数名称
            String annotation = Arrays.stream(parameter.getParameterAnnotations()).map(a -> a.annotationType().getSimpleName()).collect(Collectors.joining());
            annotation=annotation.length()>0?"@"+annotation+" ":" ";
            if (composite.supportsParameter(parameter)) {
                Object o = composite.resolveArgument(parameter, modelAndViewContainer, new ServletWebRequest(request), factory);
                //System.out.println(o.getClass());
                System.out.println("["+parameter.getParameterIndex()+"] "+annotation+parameter.getParameterType().getSimpleName()+" "+parameter.getParameterName()+"--->"+o);
                System.out.println("ModelAndViewContainer存储内容："+modelAndViewContainer.getModel());
            }else {
                System.out.println("["+parameter.getParameterIndex()+"] "+annotation+parameter.getParameterType().getSimpleName()+" "+parameter.getParameterName());
            }
        }
    }
    private static HttpServletRequest mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name1", "zhangsan");
        request.setParameter("name2", "lisi");
        request.addPart(new MockPart("file", "abc", "hello".getBytes(StandardCharsets.UTF_8)));
        // 将 id 和123对应关系存储在map中
        Map<String, String> map = new AntPathMatcher().extractUriTemplateVariables("/test/{id}", "/test/123");
        //把map放在了request作用域,这样PathVariableMethodArgumentResolver就可以获得
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, map);
        request.setContentType("application/json");  //设置请求头数据  ContentType
        request.setCookies(new Cookie("token", "123456"));
        request.setParameter("name", "张三");
        request.setParameter("age", "18");

        User user = new User("张起灵", 100);
        String s = JSON.toJSONString(user);
        request.setContent(s.getBytes(StandardCharsets.UTF_8));

        return new StandardServletMultipartResolver().resolveMultipart(request);
    }
    static class Controller{
        public void test(
                @RequestParam("name1") String name1,
                String name2,
                @RequestParam("age") int age,   //涉及到数据类型转换
                @RequestParam(name = "home",defaultValue = "${JAVA_HOME}")String home1,   //获取非请求的值
                @RequestParam("file")MultipartFile file,  //上传文件
                @PathVariable("id") int id,  //REST风格   根据注解的属性值去map里找对应的key,找到将对应的value赋给参数id
                @RequestHeader("Content-Type") String header,  //请求头
                @CookieValue("token")String token,    //Cookie里的值
                @Value("${JAVA_HOME}") String home2,   //获取Spring系统或者配置文件中的属性值
                HttpServletRequest request,
                @ModelAttribute("abc") User user1,   //将name=zhangsan&age=18与对象进行绑定，并且将参数解析结果存储到ModelAndViewContainer中  使用abc是为了指定存放在ModelAndViewContainer的名字，默认就是对象类型小写user
                User user2,  //通常我们都会使用这个方式，省略@ModelAttribute注解
                @RequestBody User user3   //请求数据（通常是json）在请求体中传过来
                ){}
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        private String name;
        private Integer age;
    }
}
