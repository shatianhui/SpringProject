package hdu.sth.test20;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * description: A20 <br>
 * date: 2022/8/2 10:43 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class A20 {
    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        // 作用：解析@RequestMapping及派生注解，生成路径与控制器方法的对应关系，在初始化时候就准备好
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        // RequestMappingInfo作为key，存放路径信息   HandlerMethod 作为value,存放方法信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k,v)->{
            System.out.println(k+"="+v);
        });
        /**
         * {GET [/test1]}=hdu.sth.test20.Controller1#test1()
         * {POST [/test2]}=hdu.sth.test20.Controller1#test2(String)
         * {PUT [/test3]}=hdu.sth.test20.Controller1#test3(String)
         * { [/test4]}=hdu.sth.test20.Controller1#test4()
         */
        // MockHttpServletRequest就是用于测试的请求对象
        HandlerExecutionChain chain = handlerMapping.getHandler(new MockHttpServletRequest("GET", "/test1"));
        System.out.println(chain);
    }
}
