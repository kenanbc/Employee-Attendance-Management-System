package com.kenanbabicipia.example;

import com.mysql.cj.protocol.Resultset;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityService {

    SQLController sqlController = SQLController.getInstance();

    public boolean addActivitiy(Activity activity) {
        String query = "INSERT INTO activity (employeeID, date, login) VALUES (?, ?, ?)";
        try {
            sqlController.connect();
            int rows = sqlController.executeUpdate(query,
                    activity.getEmployeeID(),
                    activity.getDate(),
                    activity.getLogin()
            );
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                sqlController.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Activity> selectAllActivities(){
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT employeeID, date, login, logout, totalWork FROM activity";
        try{
            sqlController.connect();
            ResultSet resultSet = sqlController.executeQuery(query);
            while(resultSet.next()){
                Activity activity = new Activity(resultSet.getInt("employeeID"), resultSet.getString("date"), resultSet.getTime("login"), resultSet.getTime("logout"), resultSet.getString("totalWork"));
                activities.add(activity);
            }
            return activities;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return activities;
    }

    public List<Activity> selectAllActivities(int employeeID){
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT employeeID, date, login, logout, totalWork FROM activity WHERE employeeID = ?";
        try{
            sqlController.connect();
            PreparedStatement preparedStatement = sqlController.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Activity activity = new Activity(resultSet.getInt("employeeID"), resultSet.getString("date"), resultSet.getTime("login"), resultSet.getTime("logout"), resultSet.getString("totalWork"));
                activities.add(activity);
            }
            return activities;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return activities;
    }

    public void updateLogOut(int employeeID){
        String querySelect = "SELECT login FROM activity WHERE employeeID = ? AND logout IS NULL";
        String queryUpdate = "UPDATE activity SET logout = ?, totalWork = ? WHERE employeeID = ? AND logout IS NULL";

        LocalTime time = LocalTime.now();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        try{
            sqlController.connect();
            PreparedStatement preparedStatementSelect = sqlController.getConnection().prepareStatement(querySelect);
            preparedStatementSelect.setInt(1, employeeID);
            ResultSet resultset = preparedStatementSelect.executeQuery();
            if (resultset.next()) {
                Time dbLogin = resultset.getTime("login");
                LocalTime loginTime = dbLogin.toLocalTime();

                LocalTime logoutTime = Time.valueOf(formattedTime).toLocalTime();

                Duration duration = Duration.between(loginTime, logoutTime);
                String totalWork = duration.toHoursPart() + ":" + duration.toMinutesPart();

                PreparedStatement preparedStatement = sqlController.getConnection().prepareStatement(queryUpdate);
                preparedStatement.setTime(1, Time.valueOf(formattedTime));
                preparedStatement.setString(2, totalWork);
                preparedStatement.setInt(3, employeeID);

            int updated = preparedStatement.executeUpdate();

            if (updated > 0) {
                System.out.println("Logout time and total work updated successfully!");
            } else {
                System.out.println("No active session found for this employee.");
            }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public boolean closingLogOut(JPanel activePanel, int employeeID){
        int confirmation = JOptionPane.showConfirmDialog(activePanel, "Would you like to close app?","Warning!", JOptionPane.YES_NO_OPTION);
        if(confirmation == JOptionPane.YES_OPTION){
            updateLogOut(employeeID);
            return true;
        }
        else return false;
    }
}