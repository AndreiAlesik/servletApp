package com.example.demo.session;

import com.example.demo.LoginDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private class UserInfo{
        String login;
        String password;
        String role;

        public UserInfo(String login, String password, String role) {
            this.login = login;
            this.password = password;
            this.role=role;

        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserInfo userInfo=null;
        try {
            userInfo=new UserInfo(request.getParameter("login"),
                    request.getParameter("pwd"),LoginDao.checkUser(request.getParameter("login"), request.getParameter("pwd")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
}