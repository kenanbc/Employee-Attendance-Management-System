package com.kenanbabicipia.example;

import java.sql.*;

public class SQLController {

    private static final String URL = "jdbc:mysql://localhost:3306/employeeattendancemanagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static SQLController instance;
    private Connection connection;

    // Privatni konstruktor za Singleton
    private SQLController() {}

    // Singleton metoda za dobijanje instance
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

    // Otvaranje konekcije
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully!");
        }
    }

    // Dobijanje konekcije
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    // Izvršavanje SELECT upita
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        connect();
        PreparedStatement preparedStatement = prepareStatement(query, params);
        return preparedStatement.executeQuery();
    }

    // Izvršavanje INSERT/UPDATE/DELETE upita
    public int executeUpdate(String query, Object... params) throws SQLException {
        connect();
        try (PreparedStatement preparedStatement = prepareStatement(query, params)) {
            return preparedStatement.executeUpdate();
        }
    }

    // Zatvaranje konekcije
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed successfully!");
        }
    }

    // Privatna pomoćna metoda za pripremu upita sa parametrima
    private PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        setParameters(preparedStatement, params);
        return preparedStatement;
    }

    // Postavljanje parametara u PreparedStatement
    private void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }
}
