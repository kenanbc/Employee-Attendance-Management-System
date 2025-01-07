package com.kenanbabicipia.example.model;

public class Request {
    private int requestID;
    private int employeeID;
    private String startDate;
    private String endDate;
    private String description;

    public Request(int employeeID, String startDate, String endDate, String description) {
        this.employeeID = employeeID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Request(String startDate, String endDate, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
