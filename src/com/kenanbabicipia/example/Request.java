package com.kenanbabicipia.example;

import java.sql.Time;

public class Request {
    private int requestID;
    private Time startDate;
    private Time endDate;
    private String description;

    public Request(int requestID, Time startDate, Time endDate, String description) {
        this.requestID = requestID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Request(Time startDate, Time endDate, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getRequestID() {
        return requestID;
    }

    public Time getStartDate() {
        return startDate;
    }

    public Time getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
