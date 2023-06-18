/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author olivier
 */
public class Place {
    public static String TABLE_NAME = "place";

    Integer id;
    String name, province;

    public Place(){
        
    }
    
    public Place(Integer id,String name, String province) {
        this.id = id;
        this.name = name;
        this.province = province;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFullName() {
        return this.name + ", " + this.province;
    }
    
    public static ArrayList<Place> getAll(Connection connection) {
        String query = "select * from place order by id";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            
            ArrayList<Place> places = new ArrayList<Place>();
            while(result.next()){
                places.add(new Place(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("province")
                ));
            }
            
            return places;
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Place getOneById(Connection connection, Integer id) {
        String query = "select * from place where id = "+ id;
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            
            Place place = new Place();
            while(result.next()){
                place.setId(result.getInt("id"));
                place.setName(result.getString("name"));
                place.setProvince( result.getString("province"));
            }
            
            return place;
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void create(Connection connection, String name, String province) {
        String query = "insert into place(name, province) values(?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            
            st.setString(1, name);
            st.setString(2, province);
            
            st.executeUpdate();

            System.out.println("place inserted");
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void update(Connection connection,Integer id, String name, String province) {
        String query = "update place set name = ?, province = ? where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);

            st.setString(1, name);
            st.setString(2, province);
            st.setInt(3, id);

            st.executeUpdate();

            System.out.println("place updated");
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void destroy(Connection connection, Integer id) {
        String query = "delete from place where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, id);

            st.executeUpdate();

            System.out.println("place destroyed");
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
