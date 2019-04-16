package com.utn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionInstance {
    private static ConnectionInstance instance;
    private Connection conn;

    public ConnectionInstance() {
        try {
            this.verifyDriver();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Singleton of instance
     *
     * @return instance of ConnectionInstance or new instance if not exists
     */
    public static ConnectionInstance getConnection() {
        if (Objects.isNull(instance)) {
            instance = new ConnectionInstance();
        }
        return instance;
    }

    /**
     * Close a connection
     *
     * @throws Exception
     */
    public void disconnect() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Connect the instance using DriverManager.getConnection
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/strategy", "root", "1234");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verify if exists the library
     *
     * @throws ClassNotFoundException
     */
    private void verifyDriver() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException: " + ex.getMessage());
            throw ex;
        }
    }

    public Connection getConn() {
        return conn;
    }
}