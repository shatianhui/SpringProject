package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * description: WebConfig <br>
 * date: 2022/8/9 9:49 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

@Component
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
        return registrationBean;
    }

    @Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext context){
        Resource welcomeResource = context.getResource("classpath:static/index.html");
        return new WelcomePageHandlerMapping(null,context,welcomeResource,"/**");
    }

    @Bean
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter(){
        return new SimpleControllerHandlerAdapter();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(ApplicationContext context){
        SimpleUrlHandlerMapping handlerMapping=new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> map = context.getBeansOfType(ResourceHttpRequestHandler.class);//寻找容器中所有ResourceHttpRequestHandler类型的处理器
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }

    @Bean
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter(){
        return new HttpRequestHandlerAdapter();
    }

    /**
     * /r1.html
     * /r2.html
     *
     * /**
     */
    @Bean ("/**")
    public ResourceHttpRequestHandler handler1(){
        ResourceHttpRequestHandler requestHandler=new ResourceHttpRequestHandler();
        List<Resource> list=new ArrayList<>();
        list.add(new ClassPathResource("static/"));
        // 添加资源处理器
        List resolvers=new ArrayList();
        resolvers.add(new CachingResourceResolver(new ConcurrentMapCache("cache1")));
        resolvers.add(new EncodedResourceResolver());
        resolvers.add(new PathResourceResolver());
        requestHandler.setResourceResolvers(resolvers);
        requestHandler.setLocations(list);
        return requestHandler;
    }

    /**
     * /img/1.jpg
     * /img/2.jpg
     *
     * /img/**
     */
    @Bean("/img/**")
    public ResourceHttpRequestHandler handler2(){
        ResourceHttpRequestHandler requestHandler=new ResourceHttpRequestHandler();
        List<Resource> list=new ArrayList<>();
        list.add(new ClassPathResource("images/"));
        requestHandler.setLocations(list);
        return requestHandler;
    }

//    @PostConstruct
//    @SuppressWarnings("all")
//    public void initGzip() throws IOException {   //为static下的html文件生成压缩文件
//        Resource resource = new ClassPathResource("static");
//        File dir = resource.getFile();
//        for (File file : dir.listFiles(pathname -> pathname.getName().endsWith(".html"))) {
//            System.out.println(file);
//            try (FileInputStream fis = new FileInputStream(file); GZIPOutputStream fos = new GZIPOutputStream(new FileOutputStream(file.getAbsoluteFile() + ".gz"))) {
//                byte[] bytes = new byte[8 * 1024];
//                int len;
//                while ((len = fis.read(bytes)) != -1) {
//                    fos.write(bytes, 0, len);
//                }
//            }
//        }
//    }
}
