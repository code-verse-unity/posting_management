/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.*;

/**
 *
 * @author olivier
 */
public class Employee {
    String id, lastName, firstName,email, civility;
    
    public Employee(){
        
    }
    
    public Employee(String id,String lastName,String firstName,String email, String civility) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.civility = civility;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCivility() {
        return civility;
    }

    public String getEmail() {
        return email;
    }
    
    
    
    public static ArrayList<Employee> getAll(Connection connection) {
        String query = "select * from employee order by id";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while(result.next()){
                employees.add(new Employee(
                        result.getString("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("email"),
                        result.getString("civility")
                ));
            }
            
            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Employee> getByName(Connection connection, String name) {
        
        String query = "select * from employee where last_name ilike '%"+ name + "%' or first_name ilike '%"+ name +"%' order by id";
        
        try {
            ResultSet result  = connection.createStatement().executeQuery(query);

            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (result.next()) {
                employees.add(new Employee(
                        result.getString("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("email"),
                        result.getString("civility")
                ));
            }

            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public static void create(Connection connection, String lastName, String firstName, String civility, String email) {
        String sql = "INSERT INTO \"employee\"(last_name, first_name, civility, email) VALUES(?,?,?,?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, civility);
            statement.setString(4, email);
            
            statement.executeUpdate();
            
            System.out.println("Employee added");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public static void update(Connection connection,String id, String lastName, String firstName, String civility, String email) {
        String sql = "UPDATE \"employee\" SET last_name = ? ,  first_name = ?, civility = ? , email = ? WHERE id = ?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, civility);
            statement.setString(4, email);
            statement.setInt(5, Integer.parseInt(id) );
            
            statement.executeUpdate();
            
            System.out.println("Employee updated");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(Connection connection, String id) {
        String sql = "DELETE FROM \"employee\" WHERE id = "+ id;

        try {
            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Employee deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
