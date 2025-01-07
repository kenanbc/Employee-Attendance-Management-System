package com.kenanbabicipia.example.service;

import com.kenanbabicipia.example.controller.SQLController;
import com.kenanbabicipia.example.model.Request;

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
            preparedStatement.execute();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Request> selectWaitingRequest(){
        List<Request> requests = new ArrayList<>();
        String query = "SELECT employeeID, startDate, endDate, description FROM request WHERE status = 'Waiting'";
        try{
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(query);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            while(resultset.next()){
                Request request = new Request(resultset.getInt("employeeID"), resultset.getString("startDate"), resultset.getString("endDate"), resultset.getString("description"));
                requests.add(request);
            }
            return requests;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return requests;
    }

    public void approveRequest(int employeeID) {
        String query = "UPDATE request SET status = 'Approved' WHERE employeeID = ? AND status LIKE 'Waiting'";

        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void denyRequest(int employeeID) {
        String query = "UPDATE request SET status = 'Denied' WHERE employeeID = ?";

        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
