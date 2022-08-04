package hdu.sth.test20;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletResponse;

/**
 * description: YmlReturnValueHandler <br>
 * date: 2022/8/2 17:34 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class YmlReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Yml yml = returnType.getMethodAnnotation(Yml.class);//获取方法注解
        return yml!=null?true:false;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //将返回结果转化成yaml字符串
        String res = new Yaml().dump(returnValue);
        //将字符串写入到响应头中
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=UTF-8");//纯文本形式
        response.getWriter().write(res);
        //设置请求已经处理完毕
        mavContainer.setRequestHandled(true);
    }
}
