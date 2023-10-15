package com.book.servlet.auth;

import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.utils.MybatisUtil;
import com.book.utils.ThymeleafUtil;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    // login页面展示给前端
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if(cookie.getName().equals("password")) password = cookie.getValue();
            }
            if(username != null && password != null){
                //登陆校验
                if (service.auth(username, password, req.getSession())) {
                    resp.sendRedirect("index");
                    return;
                }
            }
        }

        Context context = new Context();
        if(req.getSession().getAttribute("login-failure") != null){ // 检测到登录失败后
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure"); // 下次登录清除掉
        }
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
            return;
        }
        // 静态资源处理
        ThymeleafUtil.process("login.html", context, resp.getWriter());
    }

    // 登录处理
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); // 从请求头中获取信息
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");
        if(service.auth(username, password, req.getSession())){ // 判断请求头中获取的信息和数据库信息是否相等
            // 设置cookie
            if (remember != null) {
                Cookie cookie_username = new Cookie("username", username);
                cookie_username.setMaxAge(60*60*24*2);
                Cookie cookie_password = new Cookie("password", password);
                cookie_password.setMaxAge(60*60*24*2);
                resp.addCookie(cookie_username);
                resp.addCookie(cookie_password);
            }

            resp.sendRedirect("index"); // 登录成功后 向客户端发送HTTP响应 重定向到index页面
        }else {
            req.getSession().setAttribute("login-failure", new Object());
            this.doGet(req, resp);
        }
    }
}



