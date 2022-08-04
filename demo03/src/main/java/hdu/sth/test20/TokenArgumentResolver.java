package hdu.sth.test20;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * description: 自定义参数解析器 <br>
 * date: 2022/8/2 16:38 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //看参数上是否有@Token注解，如果没有，就没有必要解析
        Token token = parameter.getParameterAnnotation(Token.class);
        return token!=null?true:false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String value = webRequest.getHeader("token");
        //将请求头获取到的值直接封装到控制器方法参数上
        return value;
    }
}
