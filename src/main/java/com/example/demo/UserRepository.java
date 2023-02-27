package com.example.demo;

import java.sql.SQLException;

public class UserRepository {
    private String login;
    private String password;

    private String role;

    public UserRepository(String login, String password, String role) {
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
    public static UserRepository setUserInfo(String login, String password){
        UserRepository userInfo=null;
        try {
            userInfo=new UserRepository(login, password, LoginDao.checkUser(login,password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }
}
