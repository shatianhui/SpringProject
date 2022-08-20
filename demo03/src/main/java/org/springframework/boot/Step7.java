package org.springframework.boot;

import org.springframework.boot.logging.DeferredLog;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * description: Step7 <br>
 * date: 2022/8/13 10:15 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class Step7 {
    public static void main(String[] args) {
        // Step7:输出springboot的banner信息
        ApplicationEnvironment env = new ApplicationEnvironment();
        SpringApplicationBannerPrinter printer = new SpringApplicationBannerPrinter(new DefaultResourceLoader(), new SpringBootBanner());

        // 测试文字banner   会替代默认的SpringBootBanner实现
        /*Map<String, Object> map = new HashMap<>();
        map.put("spring.banner.location", "banner1.txt");
        env.getPropertySources().addLast(new MapPropertySource("custom",map));*/

        // 测试图片banner
//        Map<String, Object> map = new HashMap<>();
//        map.put("spring.banner.image.location", "banner2.png");
//        env.getPropertySources().addLast(new MapPropertySource("custom",map));

        // springboot版本号获取
        System.out.println(SpringBootVersion.getVersion());
        // 三个参数：第一个参数，environment对象，main方法所在类，输出的目标
        printer.print(env, Step7.class,System.out);//输出在控制台
    }
}
