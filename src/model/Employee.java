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

    Integer id;
    String lastName, firstName, email, civility, job;
    Place place;

    public static String TABLE_NAME = "employee";

    public Employee() {

    }

    public Employee(Integer id, String lastName, String firstName, String email, String civility, String job) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.civility = civility;
        this.email = email;
        this.job = job;
    }

    public Employee(Integer id, String lastName, String firstName, String email, String civility, String job, Place place) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.civility = civility;
        this.email = email;
        this.job = job;
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }

    public static ArrayList<Employee> getAll(Connection connection) {
        String query = "select * from employee order by id";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);

            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (result.next()) {
                employees.add(new Employee(
                        result.getInt("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("email"),
                        result.getString("civility"),
                        result.getString("job"),
                        Place.getOneById(connection, result.getInt("place_id"))
                ));
            }

            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Employee getOneById(Connection connection, Integer id) {
        String query = "select * from employee where id = " + id;
        try {
            ResultSet result = connection.createStatement().executeQuery(query);

            Employee employee = new Employee();
            while (result.next()) {
                employee.setId(result.getInt("id"));
                employee.setLastName(result.getString("last_name"));
                employee.setFirstName(result.getString("first_name"));
                employee.setEmail(result.getString("email"));
                employee.setCivility(result.getString("civility"));
                employee.setJob(result.getString("job"));
                employee.setPlace(Place.getOneById(connection, result.getInt("place_id")));
            }

            return employee;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Employee> getByName(Connection connection, String name) {

        String query = "select * from employee where last_name ilike '%" + name + "%' or first_name ilike '%" + name + "%' order by id";

        try {
            ResultSet result = connection.createStatement().executeQuery(query);

            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (result.next()) {
                employees.add(new Employee(
                        result.getInt("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("email"),
                        result.getString("civility"),
                        result.getString("job"),
                        Place.getOneById(connection, result.getInt("place_id"))
                ));
            }

            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<Employee> getNoPostedEmployees(Connection connection) {
        String query = "select e.* from " + Employee.TABLE_NAME + " as e "
                + "    left join " + Posting.TABLE_NAME + " as p "
                + "    on e.id = p.employee_id "
                + "    where p.employee_id is null;";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);

            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (result.next()) {
                employees.add(new Employee(
                        result.getInt("id"),
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("email"),
                        result.getString("civility"),
                        result.getString("job"),
                        Place.getOneById(connection, result.getInt("place_id"))
                ));
            }

            return employees;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void create(Connection connection, String lastName, String firstName, String civility, String email, String job, Integer placeId) {
        String sql = "INSERT INTO \"employee\"(last_name, first_name, civility, email, job,place_id) VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, civility);
            statement.setString(4, email);
            statement.setString(5, job);
            statement.setInt(6, placeId);

            statement.executeUpdate();

            System.out.println("Employee added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Connection connection, String id, String lastName, String firstName, String civility, String email, String job, Integer placeId) {
        String sql = "UPDATE \"employee\" SET last_name = ? ,  first_name = ?, civility = ? , email = ?, job = ?, place_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, civility);
            statement.setString(4, email);
            statement.setString(5, job);
            statement.setInt(6, placeId);
            statement.setInt(7, Integer.parseInt(id));

            statement.executeUpdate();

            System.out.println("Employee updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy(Connection connection, String id) {
        String sql = "DELETE FROM \"employee\" WHERE id = " + id;

        try {
            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Employee deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
