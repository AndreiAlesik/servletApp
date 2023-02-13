package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet("/updateNameOfEmployee")
public class UpdateNameOfEmployee extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sid = request.getParameter("id");
        String name = request.getParameter("name");
        int id = Integer.parseInt(sid);


        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);


        try {
            EmployeeRepository.updateName(name, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.getWriter();
        out.println("OK");
        out.close();
    }
}
