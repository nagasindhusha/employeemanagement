package com.example.employeemanagement.dao;

import com.example.employeemanagement.pojo.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDAO {

  public int registerEmployee(Employee employee) throws ClassNotFoundException {
    String INSERT_USERS_SQL = "INSERT INTO employee" +
        "  (first_name, last_name, username, password, address, contact) VALUES " +
        " (?, ?, ?, ?,?,?);";

    int result = 0;

    Class.forName("com.mysql.jdbc.Driver");

    //Step 1: Established the connection with database
    try (Connection connection = DriverManager
        .getConnection("jdbc:mysql://localhost:3306/empmanage? useSSL=false", "root",
            "1234");

         // Step 2:Create a statement using connection object
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, employee.getFirstName());
      preparedStatement.setString(2, employee.getLastName());
      preparedStatement.setString(3, employee.getUsername());
      preparedStatement.setString(4, employee.getPassword());
      preparedStatement.setString(5, employee.getAddress());
      preparedStatement.setString(6, employee.getContact());

      // Step 3: Execute the query or update query
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if(resultSet.next()) {
        result = resultSet.getInt(1);
      }
      return result;
    } catch (SQLException e) {
      // process sql exception
      printSQLException(e);
    }
    return result;
  }

  public Employee getEmployee(Integer id) throws ClassNotFoundException {
      Statement stmt;
      ResultSet rs;
      String sql = "select first_name, last_name, username, password , address, contact from employee where id =" + id.toString();
      try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/empmanage? useSSL=false", "root",
                "1234");
        stmt = connection.createStatement();
        rs = stmt.executeQuery(sql);
        rs.next();
        Employee employee = new Employee();
        employee.setFirstName(rs.getString(1));
        employee.setLastName(rs.getString(2));
        employee.setUsername(rs.getString(3));
        employee.setPassword(rs.getString(4));
        employee.setAddress(rs.getString(5));
        employee.setContact(rs.getString(6));
        rs.close();
        stmt.close();
        connection.close();
        return employee;
      }catch(SQLException e){
        e.printStackTrace();
      }
    return null;

  }

  private void printSQLException(SQLException ex) {
    for (Throwable e : ex) {
      if (e instanceof SQLException) {
        e.printStackTrace(System.err);
        System.err.println("SQLState: " + ((SQLException) e).getSQLState());
        System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
        System.err.println("Message: " + e.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
          System.out.println("Cause: " + t);
          t = t.getCause();
        }
      }
    }
  }
}