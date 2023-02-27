package com.example.demo.session;

import com.example.demo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserRepository userInfo=UserRepository.setUserInfo(request.getParameter("login"),request.getParameter("pwd"));
        SessionController.sessionSetUp(userInfo, request, response);


    }
}