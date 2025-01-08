package com.kenanbabicipia.example.controller;

import java.sql.*;

public class SQLController {

    private static final String URL = "jdbc:mysql://localhost:3306/employeeattendancemanagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static SQLController instance;
    private Connection connection;

    private SQLController() {}

    public static SQLController getInstance() {
        if (instance == null) {
            synchronized (SQLController.class) {
                if (instance == null) {
                    instance = new SQLController();
                }
            }
        }
        return instance;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully!");
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        connect();
        PreparedStatement preparedStatement = prepareStatement(query, params);
        return preparedStatement.executeQuery();
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        connect();
        try (PreparedStatement preparedStatement = prepareStatement(query, params)) {
            return preparedStatement.executeUpdate();
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed successfully!");
        }
    }

    private PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        setParameters(preparedStatement, params);
        return preparedStatement;
    }

    private void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}
