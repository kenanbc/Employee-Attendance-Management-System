package com.kenanbabicipia.example;

import com.kenanbabicipia.example.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {


    public Employee verifyLogIn(String username, String password) throws SQLException{

        Connection connection = SQLController.getInstance().getConnection();
        String sql = "SELECT employeeID, firstName, lastName, email, phoneNumber, role, username, password FROM employee WHERE username = ?";

        try(PreparedStatement query = connection.prepareStatement(sql)){
            query.setString(1, username);
            ResultSet resultSet = query.executeQuery();

            if(resultSet.next()){
                String dbPassword = resultSet.getString("password");
                if(BCrypt.checkpw(password, dbPassword)){
                    return new Employee(resultSet.getInt("employeeID"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getString("phoneNumber"), resultSet.getString("role"), resultSet.getString("username"));
                }
                else
                    return null;
            }
            else
                return null;
        }
    }

    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employee (firstName, lastName, email, phoneNumber, role, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            SQLController sqlController = SQLController.getInstance();
            int rows = sqlController.executeUpdate(query,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getRole(),
                    employee.getUsername(),
                    employee.getPassword()
                    );
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee selectEmployeeInformation(int employeeID){
        String query = "SELECT employeeID, firstName, lastName, email, role FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setInt(1, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                Employee employee = new Employee(resultset.getInt("employeeID"), resultset.getString("firstName"), resultset.getString("lastName"), resultset.getString("email"), resultset.getString("role"));
                return employee;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
