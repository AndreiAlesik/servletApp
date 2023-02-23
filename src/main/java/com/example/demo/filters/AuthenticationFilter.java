package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        PrintWriter out = res.getWriter();
        String uri = req.getRequestURI();

        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);


        if (session != null)
            out.println("session == " + session + ", user = " + session.getAttribute("role"));
        else
            out.println("session == null");


        if (session == null && !uri.endsWith("demo/loginServlet")) {
            out.println("You have to login!!!");
        }
        else if (session != null && !session.getAttribute("role").equals("admin") && !(uri.endsWith("demo/LogoutServlet") || uri.endsWith("demo/loginServlet") || uri.endsWith("demo/viewServlet"))) {
            this.context.log("<<< Unauthorized access request");
            out.println(session.getAttribute("role"));
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
    }
}