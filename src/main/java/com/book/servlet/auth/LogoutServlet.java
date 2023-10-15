package com.book.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 退出
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user"); // 清除掉登陆的状态

        // 设置cookie
        Cookie cookie_username = new Cookie("username", "username");
        cookie_username.setMaxAge(0);
        Cookie cookie_password = new Cookie("password", "password");
        cookie_password.setMaxAge(0);
        resp.addCookie(cookie_username);
        resp.addCookie(cookie_password);

        resp.sendRedirect("login");
    }
}
