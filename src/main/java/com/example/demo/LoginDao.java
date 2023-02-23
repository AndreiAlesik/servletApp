package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public static String checkUser(String login, String password) throws SQLException{
        String result="";

        Connection connection = EmployeeRepository.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select role from userreg where login=? and password=?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        connection.close();

        if(resultSet.next()){
            result=resultSet.getString(1);
        }
        return result;
    }
}
