package hdu.sth.test8_01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * description: MyControoler <br>
 * date: 2022/7/27 16:36 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@RestController
public class MyController {
    //@Lazy  //必须添加，否则会导致scope域失效，原因不知道
    @Autowired
    private BeanForRequest beanForRequest;

    @Lazy
    @Autowired
    private BeanForSession beanForSession;

    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @GetMapping(value = "/test", produces = "text/html")
    public String test(HttpServletRequest request, HttpSession session) {
        // ServletContext sc = request.getServletContext();
        String result = "<ul>" +
                "<li>request scope: " +  beanForRequest + "</li>" +
                "<li>session scope: " +  beanForSession + "</li>" +
                "<li>application scope: " +  beanForApplication + "</li>" +
                "</ul>";
        return result;
    }
}
