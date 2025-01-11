package com.kenanbabicipia.service;

import com.kenanbabicipia.controller.SQLController;
import com.kenanbabicipia.model.Activity;

import javax.swing.*;
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
        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, activity.getEmployeeID());
            preparedStatement.setString(2, activity.getDate());
            preparedStatement.setTime(3, activity.getLogin());

            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Activity> selectAllActivities(){
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT employeeID, date, login, logout, totalWork FROM activity ORDER BY activityID DESC";
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
        String query = "SELECT employeeID, date, login, logout, totalWork FROM activity WHERE employeeID = ? ORDER BY activityID DESC";
        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
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

    public List<Activity> selectAllActivities(int employeeID, String month){
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT employeeID, date, login, logout, totalWork FROM activity WHERE employeeID = ? AND SUBSTRING(date, 4, 2) = ?";
        if(month.length() == 1) month = "0".concat(month);
        if(month.length() > 2) return null;
        try{
            PreparedStatement preparedStatement = SQLController.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setString(2, month);
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
            PreparedStatement preparedStatementSelect = SQLController.getInstance().getConnection().prepareStatement(querySelect);
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

                preparedStatement.executeUpdate();

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String calculateTotalTime(JTable table, int columnIndex) {
        int totalMinutes = 0;

        for (int row = 0; row < table.getRowCount(); row++) {
            String time = (String) table.getValueAt(row, columnIndex);
            if (time != null && !time.isEmpty()) {
                String[] parts = time.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                totalMinutes += hours * 60 + minutes;
            }
        }

        int totalHours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;

        return String.format("%02d:%02d", totalHours, remainingMinutes);
    }


    public boolean closingLogOut(JPanel activePanel, int employeeID){
        int confirmation = JOptionPane.showConfirmDialog(activePanel, "Would you like to close app?\nYou will be logged out","Warning!", JOptionPane.YES_NO_OPTION);
        if(confirmation == JOptionPane.YES_OPTION){
            updateLogOut(employeeID);
            return true;
        }
        else return false;
    }

}
