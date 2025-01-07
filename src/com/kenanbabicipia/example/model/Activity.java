package com.kenanbabicipia.example.model;

import java.sql.Time;

public class Activity {

    private int activityID;
    private int employeeID;
    private String date;
    private Time login;
    private Time logout;
    private String totalWork;

    public Activity( int employeeID, String date, Time login, Time logout, String totalWork) {
        this.employeeID = employeeID;
        this.date = date;
        this.login = login;
        this.logout = logout;
        this.totalWork = totalWork;
    }

    public Activity(int employeeID, String date, Time login) {
        this.employeeID = employeeID;
        this.date = date;
        this.login = login;
    }

    public int getActivityID() {
        return activityID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getDate() {
        return date;
    }

    public Time getLogin() {
        return login;
    }

    public Time getLogout() {
        return logout;
    }

    public String getTotalWork() {
        return totalWork;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLogin(Time login) {
        this.login = login;
    }

    public void setLogout(Time logout) {
        this.logout = logout;
    }

    public void setTotalWork(String totalWork) {
        this.totalWork = totalWork;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityID=" + activityID +
                ", employeeID=" + employeeID +
                ", date='" + date + '\'' +
                ", login='" + login + '\'' +
                ", logout='" + logout + '\'' +
                ", totalWork=" + totalWork +
                '}';
    }
}
