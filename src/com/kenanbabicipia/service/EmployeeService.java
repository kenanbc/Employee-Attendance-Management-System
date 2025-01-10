package com.kenanbabicipia.service;

import com.kenanbabicipia.controller.SQLController;
import com.kenanbabicipia.model.Employee;
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
        String query = "SELECT employeeID, firstName, lastName, email, phoneNumber, role, username FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setInt(1, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                Employee employee = new Employee(resultset.getInt("employeeID"), resultset.getString("firstName"), resultset.getString("lastName"), resultset.getString("email"), resultset.getString("phoneNumber"), resultset.getString("role"), resultset.getString("username"));
                return employee;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public List<Employee> selectAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT employeeID, firstName, lastName, email, phoneNumber, role, username FROM employee";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            while(resultset.next()){
                Employee employee = new Employee(resultset.getInt("employeeID"), resultset.getString("firstName"), resultset.getString("lastName"), resultset.getString("email"), resultset.getString("phoneNumber"), resultset.getString("role"), resultset.getString("username"));
                employees.add(employee);
            }
            return employees;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    public boolean updateEmployeeInformation(Employee employee){
        String query = "UPDATE employee SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, role = ?, username = ? WHERE employeeID = ?";

        try{
          PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
          preparedStatement.setString(1, employee.getFirstName());
          preparedStatement.setString(2, employee.getLastName());
          preparedStatement.setString(3, employee.getEmail());
          preparedStatement.setString(4, employee.getPhoneNumber());
          preparedStatement.setString(5, employee.getRole());
          preparedStatement.setString(6, employee.getUsername());
          preparedStatement.setInt(7, employee.getEmployeeID());

          int rowsUpdated = preparedStatement.executeUpdate();
          return rowsUpdated > 0;

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateUsername(String username){
        String query = "SELECT employeeID FROM employee WHERE username = ?";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setString(1, username);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean validateUsername(String username, int employeeID){
        String query = "SELECT employeeID FROM employee WHERE username = ? AND employeeID <> ?";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setString(1, username);
            preparedStatementSelect.setInt(2, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean validateEmployeeID(int employeeID){
        String query = "SELECT employeeID FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setInt(1, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeEmployee(int employeeID){
        String query = "DELETE FROM employee WHERE employeeID = ?";
        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            int rows = preparedStatement.executeUpdate();

            if(rows > 0)
                return true;
            else
                return false;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
