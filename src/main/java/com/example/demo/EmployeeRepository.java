package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeRepository {

    public static void main(String[] args) throws SQLException {
//        getConnection();
//
//        Employee employee = new Employee();
//
//        employee.setName("oleg");
//        employee.setEmail(" ");
//        employee.setCountry(" ");
//        save(employee);
//
        System.out.println(LoginDao.checkUser("andrew", "andrew"));
    }

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/employee";
        String user = "postgres";
        String password = "admin";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save(Employee employee) {
        int status = 0;
        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into users(name,email,country) values (?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Employee employee) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set name=?,email=?,country=? where id=?");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setInt(4, employee.getId());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Employee getEmployeeById(int id) {

        Employee employee = new Employee();

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return employee;
    }

    public static List<Employee> getAllEmployees() {

        List<Employee> listEmployees = new ArrayList<>();

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));

                listEmployees.add(employee);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployees;
    }

    public static ResultSet getSQLQuery(String sqlQuery, String params) throws SQLException {
        Connection connection = EmployeeRepository.getConnection();
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setString(1, params);
        ResultSet rs = ps.executeQuery();
        connection.close();
        return rs;
    }
    public static void getSQL(String sqlQuery, String name, int id ) throws SQLException {
        Connection connection = EmployeeRepository.getConnection();
        PreparedStatement ps = connection.prepareStatement("update users set name=? where id=?");
        ps.setString(1, name);
        ps.setInt(2, id);

        ps.executeQuery();
        connection.close();

//        connection.close();

    }
    public Employee employeeBasic=new Employee();

    public static List<Employee> resultList(ResultSet rs) throws SQLException {
        List<Employee> outputList = new ArrayList<>();
        while (rs.next()) {

            outputList.add(new Employee(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(4),
                            rs.getString(3)
                    )
            );
        }
        return outputList;
    }
    public static final String query= "select * from users where country=?";
    public static final String queryUpdate= "update users set name=? email=? country=? where id=?";

    public static List<Employee> getUsersByCountry(String country) throws SQLException {
        ResultSet rs = getSQLQuery(query, country);
        List<Employee> listOfEmployee=resultList(rs);
        return listOfEmployee;
    }

    public static void updateName(String name, int id) throws SQLException {

        getSQL(queryUpdate,name, id);

    }
}
