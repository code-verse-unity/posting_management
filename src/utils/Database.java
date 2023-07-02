/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.*;

/**
 *
 * @author olivier
 */
public class Database {
    /**
     * TODO: update these privates variable to match your configuration
     */
    private String url;
    private String user;
    private String password;

    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Db connected");
        } catch (Exception e) {
            System.out.println("Not connected" + e.toString());
        }

        return connection;
    }
}
