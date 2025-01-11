package com.kenanbabicipia.service;

import com.kenanbabicipia.controller.SQLController;
import com.kenanbabicipia.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestService {

    public void insertNewRequest(int employeeID, String startDate, String endDate, String description){
        String query = "INSERT INTO request (employeeID, startDate, endDate, description) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Request> selectWaitingRequest(){
        List<Request> requests = new ArrayList<>();
        String query = "SELECT requestID, employeeID, startDate, endDate, description FROM request WHERE status = 'Waiting'";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            while(resultset.next()){
                Request request = new Request(resultset.getInt("requestID"), resultset.getInt("employeeID"), resultset.getString("startDate"), resultset.getString("endDate"), resultset.getString("description"));
                requests.add(request);
            }
            return requests;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return requests;
    }

    public List<Request> selectEmployeeRequest(int employeeID){
        List<Request> requests = new ArrayList<>();
        String query = "SELECT startDate, endDate, description, status FROM request WHERE employeeID = ? AND STATUS NOT LIKE 'Waiting'";

        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatementSelect.setInt(1, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            while(resultset.next()){
                Request request = new Request(resultset.getString("startDate"), resultset.getString("endDate"), resultset.getString("description"), resultset.getString("status"));
                requests.add(request);
            }
            return requests;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return requests;
    }

    public void approveRequest(int requestID) {
        String query = "UPDATE request SET status = 'Approved' WHERE requestID = ?";

        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, requestID);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void denyRequest(int requestID) {
        String query = "UPDATE request SET status = 'Denied' WHERE requestID = ?";

        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, requestID);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
