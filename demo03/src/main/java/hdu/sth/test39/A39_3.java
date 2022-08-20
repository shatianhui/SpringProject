package hdu.sth.test39;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Arrays;

/**
 * description: A39_3 <br>
 * date: 2022/8/11 16:31 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */

// --server.port=8080 debug
public class A39_3 {
    public static void main(String[] args) throws Exception {
        SpringApplication spring=new SpringApplication();
        spring.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                System.out.println("执行初始化器增强");
            }
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 2. 封装启动 args");

        DefaultApplicationArguments arguments = new DefaultApplicationArguments(args);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 8. 创建容器");  // 根据构造方法时推断的应用类型来创建容器
        GenericApplicationContext context = createApplicationContext(WebApplicationType.SERVLET);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 9. 准备容器"); //通过SpringApplication添加容器初始化器（先准备好），然后再第9步进行调用
        for (ApplicationContextInitializer initializer : spring.getInitializers()) {
            initializer.initialize(context);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 10. 加载 bean 定义");
        // 演示bean的来源：配置类
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);//将bean的定义存在DefaultListableBeanFactory
        reader.register(Config.class);
        // 演示bean的来源：XML
        XmlBeanDefinitionReader reader2 = new XmlBeanDefinitionReader(beanFactory);
        reader2.loadBeanDefinitions("classpath:b02.xml");
        // 演示bean的来源：包
        ClassPathBeanDefinitionScanner reader3 = new ClassPathBeanDefinitionScanner(beanFactory);
        reader3.scan("hdu.sth.test39.sub");

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 11. refresh 容器");
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("name:" + name + "来源：" + beanFactory.getBeanDefinition(name).getResourceDescription());
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> 12. 执行 runner");
        // 作用：执行回调run()，比如预加载一些数据
        // CommandLineRunner ApplicationRunner 两者区别在于参数上
        for (CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()) {
            runner.run(args);
        }
        for (ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()) {
            runner.run(arguments);
        }

    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context = null;
//        switch (type) {
//            case SERVLET -> context = new AnnotationConfigServletWebServerApplicationContext();
//            case REACTIVE -> context = new AnnotationConfigReactiveWebServerApplicationContext();
//            case NONE -> context = new AnnotationConfigApplicationContext();
//        }
        if(type==WebApplicationType.SERVLET){
            context = new AnnotationConfigServletWebServerApplicationContext();
        }else if (type==WebApplicationType.REACTIVE){
            context = new AnnotationConfigReactiveWebServerApplicationContext();
        }else if (type==WebApplicationType.NONE){
            context = new AnnotationConfigApplicationContext();
        }
        return context;
    }

    static class Bean4{}

    static class Bean5{}

    @Configuration
    static class Config{
        @Bean
        public Bean5 bean5(){
            return new Bean5();
        }

        @Bean
        public TomcatServletWebServerFactory tomcatServletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public CommandLineRunner commandLineRunner(){
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("CommandLineRunner()..."+ Arrays.toString(args));
                }
            };
        }

        @Bean
        public ApplicationRunner applicationRunner(){
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("ApplicationRunner()..."+Arrays.toString(args.getSourceArgs()));
                    System.out.println(args.getOptionNames());//--开头的参数
                    System.out.println(args.getOptionValues("server.port"));
                    System.out.println(args.getNonOptionArgs());
                }
            };
        }
    }
}
