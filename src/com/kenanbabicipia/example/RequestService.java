package com.kenanbabicipia.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
