/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String query = "select * from employee";
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
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
