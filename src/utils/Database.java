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
    private final String url = "jdbc:postgresql://localhost/posting_management_db";
    private final String user = "postgres";
    private final String password = "postgres";
    
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
