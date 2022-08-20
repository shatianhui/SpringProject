package hdu.sth.test40;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: HelloServlet <br>
 * date: 2022/8/15 21:11 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print("<h3>hello</h3>");
    }
}
