package com.example.demo.session;

import com.example.demo.UserRepository;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionController {
    public static void sessionSetUp(UserRepository userInfo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(userInfo!=null && !userInfo.getRole().isEmpty()){
            HttpSession session = request.getSession();
            session.setAttribute("role", userInfo.getRole());
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("login", userInfo.getLogin());
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team, " + userInfo.getLogin() + "!");
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!"+ userInfo.getLogin()+ " "+ userInfo.getPassword()+" "+ userInfo.getRole()+ "null");
        }
    }
    public static void setAuthorizationRights(ServletRequest request, ServletResponse response, HttpServletRequest req, HttpServletResponse res, ServletContext context, FilterChain chain) throws IOException, ServletException {
        PrintWriter out = res.getWriter();
        String uri = req.getRequestURI();

        context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);


        if (session != null)
            out.println("session == " + session + ", user = " + session.getAttribute("role"));
        else
            out.println("session == null");


        if (session == null && !uri.endsWith("demo/loginServlet")) {
            out.println("You have to login!!!");
        }
        else if (session != null && !session.getAttribute("role").equals("admin") && !(uri.endsWith("demo/LogoutServlet") || uri.endsWith("demo/loginServlet") || uri.endsWith("demo/viewServlet"))) {
            context.log("<<< Unauthorized access request");
            out.println(session.getAttribute("role"));
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }
}
